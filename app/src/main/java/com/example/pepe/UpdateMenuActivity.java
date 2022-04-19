package com.example.pepe;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pepe.SellerMain;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.pepe.Models.MenuItem_Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;


public class UpdateMenuActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Price;
    private EditText Caffeine;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_menu);

        Name = (EditText) findViewById(R.id.etName);
        Price = (EditText) findViewById(R.id.etPrice);
        Caffeine = (EditText) findViewById(R.id.etCaffine);
        Button add = (Button) findViewById(R.id.addButton);
        Button back = (Button) findViewById(R.id.back);

        FirebaseUser seller = FirebaseAuth.getInstance().getCurrentUser();
        String uniqueId = seller.getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        /*seller's menu*/
        CollectionReference menuReference =
                db.collection("sellers").document(uniqueId).collection("menu");

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = Name.getText().toString();
                String value1 = Price.getText().toString();
                Double p = Double.parseDouble(value1);
                String value2 = Caffeine.getText().toString();
                Integer c = Integer.parseInt(value2);
                MenuItem_Model item = new MenuItem_Model(value, p, c);

                Map<String, Object> newItem = new HashMap<>();
                newItem.put("Name", Name);
                newItem.put("Price", Price);
                newItem.put("Caffeine", Caffeine);
                menuReference.add(newItem);

                Name.setText("");
                Price.setText("");
                Caffeine.setText("");
            }
        });

        back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                backMain();
                startActivity(i);
            }
        });
    }

    private void backMain() {
        i = new Intent(this, SellerMain.class);
    }
}
