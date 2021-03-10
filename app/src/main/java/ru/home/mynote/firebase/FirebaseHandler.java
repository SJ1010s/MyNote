package ru.home.mynote.firebase;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import ru.home.mynote.MainActivity;
import ru.home.mynote.NoteStructure;
import ru.home.mynote.fragment.MainFragment;
import ru.home.mynote.fragment.adapter.AdapterMain;

import static android.content.ContentValues.TAG;

public class FirebaseHandler implements Parcelable {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public FirebaseHandler(Parcel in) {
    }

    public static final Creator<FirebaseHandler> CREATOR = new Creator<FirebaseHandler>() {
        @Override
        public FirebaseHandler createFromParcel(Parcel in) {
            return new FirebaseHandler(in);
        }

        @Override
        public FirebaseHandler[] newArray(int size) {
            return new FirebaseHandler[size];
        }
    };

    public FirebaseHandler() {

    }

    public void setFirebaseItemsInNotes(List<NoteStructure> notes, AdapterMain adapterMain) {
        firebaseFirestore.collection("notes")
                .orderBy("sort", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NoteStructure note = new NoteStructure(document.get("id").toString(), document.get("title").toString(),
                                        document.get("descr").toString(), document.get("date").toString());
                                notes.add(note);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        adapterMain.setItems(notes);
                    }
                });
    }

    public void deleteNote(String id, List<NoteStructure> notes, AdapterMain adapterMain) {
        firebaseFirestore.collection("notes")
                .document(id)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully deleted!");
                        setFirebaseItemsInNotes(notes, adapterMain);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting document", e);
                    }
                });
    }

    public void saveNote(NoteStructure note,
                         String title,
                         String descr,
                         String date,
                         String sortDateTime) {
        Map<String, Object> noteMap = new HashMap<>();
        noteMap.put("id", note.getId());
        noteMap.put("title", title);
        noteMap.put("descr", descr);
        noteMap.put("date", date);
        noteMap.put("sort", sortDateTime);
        firebaseFirestore
                .collection("notes")
                .document(note.getId())
                .set(noteMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        note.setTitle(title);
                        note.setDescr(descr);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
