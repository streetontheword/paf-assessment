package vttp2023.batch4.paf.assessment.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {

	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
	 * db.listings.aggregate([
	 * {
	 * $group: {
	 * _id: "$address.suburb"
	 * }
	 * }
	 * ]);
	 */
	public List<String> getSuburbs(String country) {
		GroupOperation groupBySuburbs = Aggregation.group("address.suburb");
		Aggregation pipeline = Aggregation.newAggregation(groupBySuburbs);
		AggregationResults<Document> results = template.aggregate(pipeline, "listings", Document.class);
		List<String> listOfSuburbs = new ArrayList<>();
		for (Document document : results) {
			String subUrbName = document.getString("_id");
			if (subUrbName.equals("")) {
				continue;
			} else {
				listOfSuburbs.add(subUrbName);
			}
		}
		return listOfSuburbs;
	}

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
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		
		Query query = Query.query(Criteria.where("address.suburb").is(suburb)
										  .and("price").lte(priceRange)
										  .and("accommodates").gte(persons)
										  .and("min_nights").gte(duration))
										  .with(Sort.by(Sort.Direction.DESC,"price")
										  );
																		
		query.fields().include("name","price","accommodates");
		List<Document> results = template.find(query, Document.class, "listings");
		List<AccommodationSummary> list = new ArrayList<>();
		for (Document d : results) {
			// System.out.println(d); // this works
			AccommodationSummary accomSum = new AccommodationSummary(); 
			accomSum.setId(d.getString("_id"));
			accomSum.setName(d.getString("name"));
			float price = d.get("price", Number.class).floatValue();
			accomSum.setPrice(price);
			accomSum.setAccomodates(d.getInteger("accommodates"));
			list.add(accomSum);
			
		}
		return list;

	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
