package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

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

@Repository
public class UsersDAO {
	public static ArrayList<String> userNames = new ArrayList<String>();
	public static Firestore db;
	public static DocumentReference docRef;

	static {
		userNames.add("Testing T. Testerston the Third");

//		FileInputStream serviceAccount;
		try {
			
		    ClassLoader classloader = Thread.currentThread().getContextClassLoader();
		    InputStream serviceAccount = classloader.getResourceAsStream("serviceAccountKey.json");

			
//			serviceAccount = new FileInputStream("./src/main/resources/serviceAccountKey.json");
			

//			serviceAccount = new FileInputStream("./bin/firebaseLogin/serviceAccountKey.json");
			FirebaseOptions options;
			options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
			FirebaseApp.initializeApp(options);
			db = FirestoreClient.getFirestore();
			docRef = db.collection("demo").document("Users");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getUserNames() throws IOException, InterruptedException, ExecutionException {
		ApiFuture<QuerySnapshot> query = db.collection("demo").get();
		QuerySnapshot querySnapshot = query.get();
		List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
		ArrayList<String> userNames = new ArrayList<String>();
		for (QueryDocumentSnapshot document : documents) {
			userNames = (ArrayList<String>) (document.get("Users"));
		}
		return userNames;
	}

	public void addUserName(String userName) {
		try {
			ArrayList<String> currentUserNames = this.getUserNames();

			ApiFuture<QuerySnapshot> query = db.collection("demo").get();
			QuerySnapshot querySnapshot = query.get();
			List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
			for (QueryDocumentSnapshot document : documents) {
				currentUserNames = (ArrayList<String>) document.get("Users");
			}
			currentUserNames.add(userName);

			Map<String, Object> testingMap = new HashMap<>();
			testingMap.put("Users", currentUserNames);

			ApiFuture<WriteResult> result = docRef.set(testingMap);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
