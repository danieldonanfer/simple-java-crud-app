package model;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Database {

	private static final String SUPABASE_URL = "https://iecbtatfvcoaepytgscu.supabase.co/rest/v1";
	private static final String ANON_KEY     = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImllY2J0YXRmdmNvYWVweXRnc2N1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3ODEwMTA4OTAsImV4cCI6MjA5NjU4Njg5MH0.ALvwCcWJx6PkE6jq3Kb6jC6EXEGKOOhwKKZYf7eaf1o";

	private List<Person> people;
	private boolean connected = false;

	public Database() {
		people = new LinkedList<Person>();
	}

	public void connect() throws Exception {
		// Testa se a API do Supabase está acessível
		URL url = new URL(SUPABASE_URL + "/pessoas?limit=1");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("apikey", ANON_KEY);
		conn.setRequestProperty("Authorization", "Bearer " + ANON_KEY);
		conn.setConnectTimeout(10000);
		int code = conn.getResponseCode();
		if (code >= 200 && code < 300) {
			connected = true;
			System.out.println("Conectado ao Supabase via REST API. Status: " + code);
		} else {
			throw new Exception("Supabase retornou status: " + code);
		}
		conn.disconnect();
	}

	public void disconnect() {
		connected = false;
	}

	public void save() throws Exception {
		if (!connected) throw new Exception("Sem conexao com o banco de dados");

		for (Person p : people) {
			// UPSERT: POST com Prefer: resolution=merge-duplicates (insert ou update pelo id)
			String json = toJson(p);
			URL upsertUrl = new URL(SUPABASE_URL + "/pessoas");
			HttpURLConnection upsertConn = (HttpURLConnection) upsertUrl.openConnection();
			upsertConn.setRequestMethod("POST");
			upsertConn.setRequestProperty("apikey", ANON_KEY);
			upsertConn.setRequestProperty("Authorization", "Bearer " + ANON_KEY);
			upsertConn.setRequestProperty("Content-Type", "application/json");
			upsertConn.setRequestProperty("Prefer", "resolution=merge-duplicates,return=minimal");
			upsertConn.setDoOutput(true);
			upsertConn.setConnectTimeout(10000);
			upsertConn.getOutputStream().write(json.getBytes(StandardCharsets.UTF_8));
			int code = upsertConn.getResponseCode();
			if (code >= 400) {
				String err = readResponse(upsertConn);
				throw new Exception("Erro ao salvar " + p.getName() + ": HTTP " + code + " - " + err);
			}
			upsertConn.disconnect();
			System.out.println("UPSERT " + p.getName() + " -> HTTP " + code);
		}
	}

	public void load() throws Exception {
		if (!connected) return;
		people.clear();

		URL url = new URL(SUPABASE_URL + "/pessoas?order=nome");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("apikey", ANON_KEY);
		conn.setRequestProperty("Authorization", "Bearer " + ANON_KEY);
		conn.setConnectTimeout(10000);
		String body = readResponse(conn);
		conn.disconnect();

		// Parse JSON simples
		parseJsonArray(body);
	}

	private String toJson(Person p) {
		return "{"
			+ "\"id\":" + p.getId() + ","
			+ "\"nome\":\"" + escape(p.getName()) + "\","
			+ "\"cpf\":\"" + escape(p.getTaxId()) + "\","
			+ "\"nascimento\":\"" + escape(p.getDataNascimento()) + "\","
			+ "\"genero\":\"" + p.getGender().name() + "\","
			+ "\"email\":\"" + escape(p.getEmail()) + "\","
			+ "\"telefone\":\"" + escape(p.getTelefone()) + "\","
			+ "\"cargo\":\"" + escape(p.getOccupation()) + "\","
			+ "\"contrato\":\"" + p.getEmpCat().name() + "\","
			+ "\"ativo\":" + p.isAtivo()
			+ "}";
	}

	private String escape(String s) {
		if (s == null) return "";
		return s.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	private String readResponse(HttpURLConnection conn) throws IOException {
		InputStream is;
		try {
			is = conn.getInputStream();
		} catch (IOException e) {
			is = conn.getErrorStream();
			if (is == null) return "";
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) sb.append(line);
		br.close();
		return sb.toString();
	}

	private void parseJsonArray(String json) {
		// Parser manual simples para o JSON retornado pelo Supabase
		json = json.trim();
		if (json.equals("[]") || json.isEmpty()) return;
		// Remove [ e ]
		json = json.substring(1, json.length() - 1).trim();

		// Divide por objetos },...,{
		List<String> objects = splitObjects(json);
		for (String obj : objects) {
			try {
				int id             = Integer.parseInt(getField(obj, "id"));
				String nome        = getField(obj, "nome");
				String cpf         = getField(obj, "cpf");
				String nascimento  = getField(obj, "nascimento");
				String genero      = getField(obj, "genero");
				String email       = getField(obj, "email");
				String telefone    = getField(obj, "telefone");
				String cargo       = getField(obj, "cargo");
				String contrato    = getField(obj, "contrato");
				boolean ativo      = Boolean.parseBoolean(getField(obj, "ativo"));

				Gender g = Gender.valueOf(genero);
				EmploymentCategory emp = EmploymentCategory.valueOf(contrato);

				Person person = new Person(id, nome, cargo, nascimento, emp, cpf, ativo, g);
				person.setEmail(email);
				person.setTelefone(telefone);
				people.add(person);
			} catch (Exception e) {
				System.err.println("Erro ao parsear pessoa: " + e.getMessage());
			}
		}
	}

	private List<String> splitObjects(String json) {
		List<String> result = new ArrayList<>();
		int depth = 0;
		int start = 0;
		for (int i = 0; i < json.length(); i++) {
			char c = json.charAt(i);
			if (c == '{') { if (depth == 0) start = i; depth++; }
			else if (c == '}') { depth--; if (depth == 0) result.add(json.substring(start, i + 1)); }
		}
		return result;
	}

	private String getField(String json, String key) {
		String search = "\"" + key + "\":";
		int idx = json.indexOf(search);
		if (idx < 0) return "";
		int start = idx + search.length();
		char first = json.charAt(start);
		if (first == '"') {
			int end = json.indexOf('"', start + 1);
			return json.substring(start + 1, end);
		} else {
			int end = start;
			while (end < json.length() && json.charAt(end) != ',' && json.charAt(end) != '}') end++;
			return json.substring(start, end).trim();
		}
	}

	public void addPerson(Person person) {
		people.add(person);
	}

	public void removePerson(int index) {
		people.remove(index);
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList(people);
	}

	public void saveToFile(File file) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
			oos.writeObject(people.toArray(new Person[0]));
		}
	}

	public void loadFromFile(File file) throws IOException {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
			Person[] persons = (Person[]) ois.readObject();
			people.clear();
			people.addAll(Arrays.asList(persons));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
