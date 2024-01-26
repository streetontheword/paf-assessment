package vttp2023.batch4.paf.assessment;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import vttp2023.batch4.paf.assessment.services.ForexService;

@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}
	@Autowired
	MongoTemplate mongoTemp;
	@Autowired
	ForexService svc; 

	@Override
	public void run(String... args) throws Exception {

		svc.convert("AUD", "SGD", 200);

		/*
	 * db.listings.find({
	 * "address.suburb": "Coogee",
	 * "price": {$lte:200},
	 * "accommodates": {$gte:1} ,
	 * "minimum_nights": {$lte: 21}
	 * },
	 * { name: 1, price: 1, accommodates: 1, "address.suburb": 1, minimum_nights: 1}
	 * )
	 */

	// Query query = Query.query(Criteria.where("address.suburb").is("Coogee")
	// 									  .and("price").lte(200)
	// 									  .and("accommodates").gte(1)
	// 									  .and("minimum_nights").is(21));
										 
										  

	// 	query.fields().include("name","price","accommodates","minimum_nights");
	// 	List<Document> results = mongoTemp.find(query, Document.class, "listings");
	// 	System.out.println(results);
	// 	for (Document d : results) {
	// 		System.out.println(d); // this works
		}
	}

