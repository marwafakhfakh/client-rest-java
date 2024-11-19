package ws.rest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import spring.jpa.model.Produit;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
public class MainClient {
	public static void main( String[] args )
	{
	System.out.println("Démarrage du Client....");
	// Objet de configuration
	ClientConfig config = new DefaultClientConfig();
	//objet client
	Client client = Client.create(config);
	//créer l'uri
	URI uri =
	UriBuilder.fromUri("http://localhost:8080/produits").build();
	//obtenir une resource correspondante à l'uri du service web
	WebResource service = client.resource(uri);
	//Requête GET
	System.out.println( "***********************************************" );
	System.out.println("Méthode GET - Afficher tous les produits....");
	//référencer la méthode "getAllProduits"
	WebResource resource= service.path("/");
	//passer la méthode "get"
	String reponseGetAllProduits= resource.accept(MediaType.APPLICATION_JSON).get(String.class);
	//Afficher la réponse textuelle
	System.out.println(reponseGetAllProduits);
	
	//Requête POST
	System.out.println( "***********************************************" );
	System.out.println( "Méthode POST - Ajouter un nouveau produit...." );
	//référencer la méthode "saveProduit"
	WebResource resourceSave= service.path("/") ;
	Produit nouveauProduit =new Produit("Biscuit", 0.800, 15);
	//passer la méthode « post »
	Produit reponseSaveProduit= resourceSave.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
	.post(Produit.class, nouveauProduit) ;
	// Afficher la réponse textuelle de l'opération d'ajout
	System.out.println(reponseSaveProduit) ;
	// Récupérer des objets "Produit" en utilisant l'API gson de Google
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	JsonArray jo = new
	JsonParser().parse(reponseGetAllProduits).getAsJsonArray();
	Produit[] listeP = gson.fromJson(jo,Produit[].class);
	System.out.println(
	"***********************************************" );
	System.out.println( "Liste des produits (API gson)....");
	for (Produit p: listeP)
	{
	System.out.println(p);
	}
	}
}