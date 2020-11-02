package com.cgcdoss.starwars.api.repositories;

import static com.mongodb.client.model.Filters.eq;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.bson.BsonDocument;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.cgcdoss.starwars.api.entities.Planeta;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Repository
public class MongoDBPlanetaRepository implements PlanetaRepository {

	private static final TransactionOptions txnOptions = TransactionOptions.builder().readPreference(ReadPreference.primary()).readConcern(ReadConcern.MAJORITY).writeConcern(WriteConcern.MAJORITY).build();

	@Value("${spring.data.mongodb.uri}")
	private String mongodbUri;

	@Autowired
	private MongoClient client;
	private MongoCollection<Planeta> planetaCollection;

	CodecRegistry pojoCodecRegistry;

	@PostConstruct
	void init() {
		ConnectionString connectionString = new ConnectionString(mongodbUri);
		pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString).codecRegistry(codecRegistry).build();

		client = MongoClients.create(clientSettings);
		MongoDatabase mongoDatabase = client.getDatabase("starwars");
		planetaCollection = mongoDatabase.getCollection("starwars", Planeta.class);
	}

	@Override
	public Planeta save(Planeta planeta) {
		planetaCollection.withCodecRegistry(pojoCodecRegistry);
		planeta.setId(new ObjectId().toString());
		planetaCollection.insertOne(planeta);
		return planeta;
	}

	@Override
	public List<Planeta> findAll() {
		return planetaCollection.find().into(new ArrayList<>());
	}

	@Override
	public long deleteById(String id) {
		return planetaCollection.deleteOne(eq("_id", id)).getDeletedCount();
	}

	@Override
	public long deleteAll() {
		try (ClientSession clientSession = client.startSession()) {
			return clientSession.withTransaction(
					() -> planetaCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(),
					txnOptions);
		}
	}

	@Override
	public Optional<Planeta> findById(String id) {
		return Optional.ofNullable(planetaCollection.find(eq("_id", id)).first());
	}

	@Override
	public Optional<Planeta> findByNome(String nome) {
		return Optional.ofNullable(planetaCollection.find(eq("nome", nome)).first());
	}

}
