package com.example.quakerecap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<PojoClass> earthquakes = QuakeQuery.QueryClass.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView list_view = (ListView) findViewById(R.id.list_view);

        // Create a new {@link ArrayAdapter} of earthquakes
        final PojoAdapter adapter= new PojoAdapter(
                MainActivity.this,earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        list_view.setAdapter(adapter);




        //***********GESTION D'EVENEMENT DE CLICK SUR LES ELEMENTS DE LA LISTVIEW*******************//

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                // RECUPERER L'EVENEMENT DE CLICK SELON L'ITEM CLICKÉ QUI N'EST RIEN D AUTRE
                // QU'UN OBJET DE LA ARRAYLIST QUI EST DANS LA LISTVIEW

             PojoClass positionViewCliked = adapter.getItem(position);

             // CREER L'INTENT D'OUVRIR UNE PAGE WEB AVEC LA SYNTAX CLASSIQUE
                // L'URL EST DANS LA ARRAY LIST RECUPERÉE DEPUIS LA REQUETTE JSON
                //DONC L'OBJET DE L'EVENEMENT CLICKÉ POINT GET URL ET CA PASSE !!!!!!

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(positionViewCliked.getUrl()));


                // DEMARRER L INTENT SI UNE APPLICATION POUVANT LE GERER EXISTE SUR LE PHONE !!!
                if (websiteIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(websiteIntent);
                }


            }
        });
    }
    }
