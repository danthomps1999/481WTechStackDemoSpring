package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Repository
public class UsersDAO {
	public static ArrayList<String> userNames = new ArrayList<String>();

	static {
		userNames.add("Testing T. Testerston the Third");
	}

	public ArrayList<String> getUserNames() throws IOException, InterruptedException, ExecutionException {
		FileInputStream serviceAccount = new FileInputStream("./bin/firebaseLogin/serviceAccountKey.json");
		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();
		FirebaseApp.initializeApp(options);
		Firestore db = FirestoreClient.getFirestore();
		DocumentReference docRef = db.collection("demo").document("Users");
		ApiFuture<QuerySnapshot> query = db.collection("demo").get();
		QuerySnapshot querySnapshot = query.get();

//		return userNames;
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		ArrayList<String> userNames = new ArrayList<String>();
		for (QueryDocumentSnapshot document : documents) {
			userNames.add(document.get("Users").toString());
		}
		return userNames;
	}

	public void addUserName(String userName) {
		userNames.add(userName);
	}
}
