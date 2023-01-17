package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class TestMain {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
//		System.out.println("Hello");
//
//		// Use the application default credentials
//		GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
//		FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).setProjectId(projectId)
//				.build();
//		FirebaseApp.initializeApp(options);
//
//		Firestore db = FirestoreClient.getFirestore();

		FileInputStream serviceAccount = new FileInputStream("./bin/firebaseLogin/serviceAccountKey.json");

		FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.build();

//				.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/").build();

		FirebaseApp.initializeApp(options);

		System.out.println("Hello World!");

		Firestore db = FirestoreClient.getFirestore();

		DocumentReference docRef = db.collection("demo").document("Users");

		ArrayList<String> testInput = new ArrayList<String>();
		testInput.add("Testing Testerson");
		testInput.add("Testing Testerson1");
		testInput.add("Testing Testerson2");
		testInput.add("Testing Testerson3");

		Map<String, Object> testingMap = new HashMap<>();
		testingMap.put("Users", testInput);

		ApiFuture<WriteResult> result = docRef.set(testingMap);

		// System.out.println(result);

		ApiFuture<QuerySnapshot> query = db.collection("demo").get();
		QuerySnapshot querySnapshot = query.get();

		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		for (QueryDocumentSnapshot document : documents) {
			System.out.println(document.get("Users").toString());
		}

	}

}
