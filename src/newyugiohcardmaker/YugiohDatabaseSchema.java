package newyugiohcardmaker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class YugiohDatabaseSchema{
    public static void main(String[] args){
        Connection conn = null;
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //Load the driver
            conn = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1\\AD-DE64:1433;databaseName=yugiohDB;user=yugi;password=muto"); //Connect
            
            DropAllViews(conn);
            DropAllTables(conn);
            CreateAllTables(conn);
            CreateAllViews(conn);
        }catch(Exception e){
            System.out.println(e);
        }finally{
            try{
                conn.close();
            }catch(Exception e){ /* ignored */ }
        }
    }

    private static void CreateAllViews(Connection conn) throws ClassNotFoundException, SQLException{
        conn.createStatement().execute("CREATE VIEW [dbo].[normal_monsters] AS "
                + "SELECT * "
                + "FROM ("
                    + "select c.id, c.name, p.property_name as property_name, p.value as value "
                    + "FROM cards c inner join properties p on c.id = p.card_id "
                    + "where c.id in ("
                        + "select card_id from properties where property_name in ('level', 'atk', 'def') group by card_id having count(card_id) = 3"
                    + ") and c.category in ('normal')"
                + ")t "
                + "PIVOT("
                    + "max(value)"
                    + "FOR property_name IN ([level], [atk], [def])"
                + ") as piv;");
    }

    private static void CreateAllTables(Connection conn) throws ClassNotFoundException, SQLException{
        conn.createStatement().execute("CREATE TABLE[dbo].[cards]("
                + " [id] [int] NULL,"
                + " [name] [varchar](50) NULL,"
                + " [serial_number] [varchar](50) NULL,"
                + " [description] [varchar](50) NULL,"
                + " [category] [varchar](50) NULL"
                + ") ON[PRIMARY]");

        conn.createStatement().execute("CREATE TABLE[dbo].[artworks]("
                + " [image_path] [varchar](50) NULL,"
                + " [card_id] [int] NULL"
                + ") ON[PRIMARY]");

        conn.createStatement().execute("CREATE TABLE[dbo].[properties]("
                + " [property_name] [varchar](50) NULL,"
                + " [value] [varchar](50) NULL,"
                + " [data_type] [varchar](50) NULL,"
                + " [card_id] [int] NULL"
                + ") ON[PRIMARY]");
    }

    private static void DropAllViews(Connection conn) throws ClassNotFoundException, SQLException{
        conn.createStatement().execute("DROP VIEW normal_monsters");
    }

    private static void DropAllTables(Connection conn) throws ClassNotFoundException, SQLException{
        conn.createStatement().execute("DROP TABLE cards");
        conn.createStatement().execute("DROP TABLE artworks");
        conn.createStatement().execute("DROP TABLE properties");
    }
/*
    private static void PopulateDatabase(Connection conn) throws ClassNotFoundException, SQLException, FileNotFoundException{
        JSONParser parser = new JSONParser();
        
            Object obj = parser.parse(new FileReader("/Users/<username>/Documents/file1.txt"));
 
            JSONObject jsonObject = (JSONObject) obj;
 
            String name = (String) jsonObject.get("Name");
            String author = (String) jsonObject.get("Author");
    }
*/
}
