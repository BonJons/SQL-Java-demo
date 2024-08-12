import java.sql.*;
import java.util.*;
import java.io.*;
public class Movie_main {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		// Driver definition
		String DATABASE_DRIVER = "com.mysql.jdbc.Driver";
		
		//Initialize connection object.
		Connection con;
		
		String dbURL = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "password";



		System.out.println("Reading files.");


		// Lists of objects created from 
		// csv's
		List<Genre> h = new ArrayList<>();
		List<Keywords> k = new ArrayList<>();
		List<Movie> movies = new ArrayList<>();
		List<Lang> languages = new ArrayList<>();
		List<Cast> casts = new ArrayList<>();
		List<Crew> crews = new ArrayList<>();
		List<Country> countries = new ArrayList<>();
		List<Production> prods = new ArrayList<>();

		
		// reading in csvs.
		
		try (Scanner genreScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\genres.csv")))){

			String top = genreScan.nextLine();

			while(genreScan.hasNextLine())
			{
				String s = genreScan.nextLine();
				String[] n = s.split(",");


				Genre g = new Genre(n[0], n[1]);
				h.add(g);

			}
			genreScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		try (Scanner kWordScan = new Scanner(new File("C:\\Users\\jon_s\\OneDrive\\Desktop\\keywords.csv"))){


			while(kWordScan.hasNextLine())
			{
				String s = kWordScan.nextLine();
				String[] n = s.split(",");


				Keywords g = new Keywords(n[0], n[1]);
				k.add(g);

			}
			kWordScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		// reading in movie csv, original file was
		// somehow corrupted so had to copy and
		// paste into notepad++ and somehow fixed it
		try (Scanner movieScan = new Scanner(new File(("movie.csv")))){

			//String top = movieScan.nextLine();

			while(movieScan.hasNextLine())
			{
				String s = movieScan.nextLine();
				String[] n = s.split(",");

				Movie mm = new Movie(n[0], n[1], n[2]);
				movies.add(mm);

			}
			movieScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		try (Scanner langScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\spoken_languages.csv")))){

			
			while(langScan.hasNextLine())
			{
				String s = langScan.nextLine();
				String[] n = s.split(",");

				Lang L = new Lang(n[0], n[1], n[2]);
				languages.add(L);

			}
			langScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		try (Scanner castScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\cast.csv")))){

			
			while(castScan.hasNextLine())
			{
				String s = castScan.nextLine();
				String[] n = s.split(",");

				Cast c = new Cast(n[0], n[2], n[4], n[5]);
				casts.add(c);

			}
			castScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		try (Scanner crewScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\crew.csv")))){

			
			while(crewScan.hasNextLine())
			{
				String s = crewScan.nextLine();
				String[] n = s.split(",");

				Crew cr = new Crew(n[0], n[1], n[2], n[3], n[4], n[5]);
				crews.add(cr);

			}
			crewScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		

		
		try (Scanner countryScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\countries.csv")))){

			
			while(countryScan.hasNextLine())
			{
				String s = countryScan.nextLine();
				String[] n = s.split(",");

				Country cu = new Country(n[0], n[1], n[2]);
				countries.add(cu);

			}
			countryScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		try (Scanner prodScan = new Scanner(new File(("C:\\Users\\jon_s\\OneDrive\\Desktop\\production.csv")))){

			
			while(prodScan.hasNextLine())
			{
				String s = prodScan.nextLine();
				String[] n = s.split(",");

				Production pr = new Production(n[0], n[1], n[2]);
				prods.add(pr);

			}
			prodScan.close();

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(dbURL, username, password);
			if(con != null)
			{
				System.out.println("good connection");
				Statement m = con.createStatement();

				// creating database and associated tables
				m.executeUpdate("DROP DATABASE IF EXISTS movies_final");

				m.executeUpdate("CREATE DATABASE IF NOT EXISTS movies_final");

				System.out.println("Database created.");

				m.executeUpdate("USE movies_final");
				
				// creating tables
				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS movie ("
						+ "movie_id VARCHAR(50) PRIMARY KEY, "
						+ "title VARCHAR(100),"
						+ "released VARCHAR(25))");

				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS genres ("
						+ "genre_id VARCHAR(50) PRIMARY KEY, "
						+ "genre VARCHAR(100))");

				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS keywords ("
						+ "word_id VARCHAR(50) PRIMARY KEY, "
						+ "keywords VARCHAR(100))");

				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS languages ("
						+ "language VARCHAR(500), "
						+ "movie_id VARCHAR(50), "
						+ "abbreviation VARCHAR(10), "
						+ "FOREIGN KEY (movie_id) REFERENCES movie(movie_id))");
				
				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS cast ("
						+ "movie_id VARCHAR(50), "
						+ "actor VARCHAR(100), "
						+ "portrayal VARCHAR(610), "
						+ "num VARCHAR(5), "
						+ "PRIMARY KEY (movie_id, actor, num, portrayal), "
						+ "FOREIGN KEY (movie_id) REFERENCES movie(movie_id))");
					
				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS crew ("
						+ "movie_id VARCHAR(20), "
						+ "num INT(10), "
						+ "name VARCHAR(200), "
						+ "secondary_num INT(10), "
						+ "department VARCHAR(100), "
						+ "job VARCHAR(100), "
						+ "PRIMARY KEY (movie_id, name, num, secondary_num, job), "
						+ "FOREIGN KEY (movie_id) REFERENCES movie(movie_id))");
				
				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS countries ("
						+ "movie_id VARCHAR(20), "
						+ "abbrev VARCHAR(200), "
						+ "country VARCHAR(50), "
						+ "PRIMARY KEY (movie_id, country), "
						+ "FOREIGN KEY (movie_id) REFERENCES movie(movie_id))");

				
				m.executeUpdate("CREATE TABLE IF NOT EXISTS production ("
						+ "movie_id VARCHAR(20), "
						+ "prod_id VARCHAR(50), "
						+ "company VARCHAR(100), "
						+ "PRIMARY KEY (movie_id, company), "
						+ "FOREIGN KEY (movie_id) REFERENCES movie(movie_id))");

				System.out.println("Tables created.");
				
				
				// inserting into tables
				
				String g_insert = "INSERT INTO genres (genre_id, genre) VALUES(?, ?)";

				try(PreparedStatement p = con.prepareStatement(g_insert))
				{
					int num = 0;
					for(Genre g: h)
					{
						p.setString(1, g.getId());
						p.setString(2, g.getGenre());
						p.addBatch();
						num++;
						
						if(num % 500 == 0)
						{
							p.executeBatch();
						}
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				

				String p_insert = "INSERT INTO keywords (word_id, keywords) VALUES(?, ?)";

				try(PreparedStatement p = con.prepareStatement(p_insert))
				{
					int num = 0;
					for(Keywords kw: k)
					{
						p.setString(1, kw.getId());
						p.setString(2, kw.getkWord());
						p.addBatch();
						num++;
						
						if(num % 500 == 0)
						{
							p.executeBatch();
						}
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}

				
				
				String m_insert = "INSERT INTO movie (movie_id, title, released) VALUES(?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(m_insert))
				{
					int num = 0;
					
					for(Movie mov: movies)
					{
						p.setString(1, mov.getId());
						p.setString(2, mov.getTitle());
						p.setString(3, mov.getReleased());
						p.addBatch();
						num++;
						
						if(num % 500 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
				String l_insert = "INSERT INTO languages (language, movie_id, abbreviation) VALUES(?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(l_insert))
				{
					int num = 0;
					
					for(Lang lan: languages)
					{
						p.setString(1, lan.getLang());
						p.setString(2, lan.getId());
						p.setString(3, lan.getAbb());
						p.addBatch();
						num++;
						
						if(num % 500 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
				String c_insert = "INSERT INTO cast (movie_id, actor, num, portrayal) VALUES(?, ?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(c_insert))
				{
					int num = 0;
					
					for(Cast cast: casts)
					{
						p.setString(1, cast.getId());
						p.setString(2, cast.getActor());
						p.setString(3, cast.getNum());
						p.setString(4, cast.getCharacter());
						p.addBatch();
						num++;
						
						if(num % 10000 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
				String cr_insert = "INSERT INTO crew (movie_id, num, name, secondary_num, department, job)"
						+ " VALUES(?, ?, ?, ?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(cr_insert))
				{
					int num = 0;
					
					for(Crew cre: crews)
					{
						p.setString(1, cre.getId());
						p.setInt(2, Integer.parseInt(cre.getAltId()));
						p.setString(3, cre.getName());
						p.setInt(4, Integer.parseInt(cre.getNum()));
						p.setString(5, cre.getDep());
						p.setString(6, cre.getJob());
						p.addBatch();
						num++;
						
						if(num % 10000 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
				String cun_insert = "INSERT INTO countries (movie_id, abbrev, country) VALUES(?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(cun_insert))
				{
					int num = 0;
					
					for(Country country: countries)
					{
						p.setString(1, country.getId());
						p.setString(2, country.getAbb());
						p.setString(3, country.getCountry());
						p.addBatch();
						num++;
						
						if(num % 1000 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
				String pr_insert = "INSERT INTO production (movie_id, prod_id, company) VALUES(?, ?, ?)";

				try(PreparedStatement p = con.prepareStatement(pr_insert))
				{
					int num = 0;
					
					for(Production prod: prods)
					{
						p.setString(1, prod.getId());
						p.setString(2, prod.getProdId());
						p.setString(3, prod.getCompany());
						p.addBatch();
						num++;
						
						if(num % 1000 == 0)
						{
							p.executeBatch();
						}
					
					}

					p.executeBatch();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				
				
			}
			else
			{
				System.out.println("meow");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}


		System.out.println("Done");

		
	}
	
	// classes to load in data

	private static class Genre{
		private String Id;
		private String genre;


		public Genre(String s, String n)
		{
			setId(s);
			setGenre(n);
		}

		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
		}
		public String getGenre() {
			return genre;
		}
		public void setGenre(String genre) {
			this.genre = genre;
		}


	}
	private static class Keywords{

		private String Id;
		private String kWord;

		public Keywords(String s, String n)
		{
			setId(s);
			setkWord(n);
		}

		public String getId() {
			return Id;
		}
		public void setId(String id) {
			Id = id;
		}
		public String getkWord() {
			return kWord;
		}
		public void setkWord(String kWord) {
			this.kWord = kWord;
		}


	}	
	private static class Movie{

		private String Id;
		private String title;
		private String released;

		public Movie(String id, String title, String released)
		{
			setId(id);
			setTitle(title);
			setReleased(released);
		}

		public String getId() {
			return Id;
		}

		public void setId(String id) {
			Id = id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getReleased() {
			return released;
		}

		public void setReleased(String released) {
			this.released = released;
		}
	}
	private static class Lang{
		
		private String id;
		private String abb;
		private String lang;
		
		public Lang(String id, String abb, String lang)
		{
			setId(id);
			setAbb(abb);
			setLang(lang);
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAbb() {
			return abb;
		}
		public void setAbb(String abb) {
			this.abb = abb;
		}
		public String getLang() {
			return lang;
		}
		public void setLang(String lang) {
			this.lang = lang;
		}
	}
	private static class Cast{
		private String id;
		private String actor;
		private String character;
		private String num;
		
		public Cast(String id, String actor, String num, String character)
		{
			setId(id);
			setActor(actor);
			setCharacter(character);
			setNum(num);
		}
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getActor() {
			return actor;
		}
		public void setActor(String actor) {
			this.actor = actor;
		}
		public String getCharacter() {
			return character;
		}
		public void setCharacter(String character) {
			this.character = character;
		}


		public String getNum() {
			return num;
		}


		public void setNum(String num) {
			this.num = num;
		}
	}
	private static class Crew{
		
		private String id;
		private String altId;
		private String name;
		private String num;
		private String dep;
		private String job;
		
		public Crew(String id, String altId, String name
				, String num, String dep, String job){
			setId(id);
			setAltId(altId);
			setName(name);
			setNum(num);
			setDep(dep);
			setJob(job);
		}
		
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAltId() {
			return altId;
		}
		public void setAltId(String altId) {
			this.altId = altId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNum() {
			return num;
		}
		public void setNum(String num) {
			this.num = num;
		}
		public String getDep() {
			return dep;
		}
		public void setDep(String dep) {
			this.dep = dep;
		}
		public String getJob() {
			return job;
		}
		public void setJob(String job) {
			this.job = job;
		}
		
		
		
		
	}
	private static class Country{
		
		private String id;
		private String abb;
		private String country;
		
		public Country(String id, String abb, String country)
		{
			setId(id);
			setAbb(abb);
			setCountry(country);
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getAbb() {
			return abb;
		}
		public void setAbb(String abb) {
			this.abb = abb;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		
		
		
		
	}
	private static class Production{
		
		String id;
		String prodId;
		String company;
		
		public Production(String id, String prodID, String company)
		{
			setId(id);
			setProdId(prodID);
			setCompany(company);
		}
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getProdId() {
			return prodId;
		}
		public void setProdId(String prodId) {
			this.prodId = prodId;
		}
		public String getCompany() {
			return company;
		}
		public void setCompany(String company) {
			this.company = company;
		}
		
		
	}


}