package com.example.quakerecap;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class PojoAdapter extends ArrayAdapter<PojoClass> {
    //*************************//
    private String place;
    private String place_offset;
    private static final String SPLITTER_REF = " of ";
    //**************************//


    public PojoAdapter(Activity context, ArrayList<PojoClass> earthquakes) {

        super(context, 0, earthquakes);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.quakepojo_view, parent, false);

        }
        //***************************************************//

        PojoClass pojoClass = getItem(position);




         //RECUPERER LA TEXTVIEW DE L'ID MAGNITUDE
        TextView magnitudeView = (TextView) listItemView.findViewById(R.id.magnitude);

         //APPELATION DE LA METHODE POUR FORMATER LE DOUBLE RECUPERÉ DEPUIS LA REQUETTE JSON ET LE STOCKER
        String magnitudeFormatted= formatMagnitude(pojoClass.getMagnitude());
        magnitudeView.setText(magnitudeFormatted);

        //RECUPERER LE BACKGROUND DE LA MAGNITUDE VIEW QU'ON A PERSONNALISÉ DANS MAGNITUDE_CIRCLE XML
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

           // RECUPERATION DU RETOUR DE LA METHODE OUBIEN SON OUTPUT QUI EST UNE COULEUR SELON LA MAGNITUDE
          int magnitudeColor = getMagnitudeColor(pojoClass.getMagnitude());
          magnitudeCircle.setColor(magnitudeColor);
        // **************************************************************************************//


        // SI LA PLACE (STRING) CONTIENT LE CHARACTERE " OF "
        if (pojoClass.getPlace().contains(SPLITTER_REF)) {

            // LA FRAGMENTER A PARTIR DU CHARACTERE " OF " ET STOCKER LES DEUX FRAGMENTS DANS UN TABLEAU
            String[] parts = pojoClass.getPlace().split(SPLITTER_REF);

            // STOCKER LA PREMIERE PARTIE DU FRAGMENT + LE REFERENT DU FRAGMENT
            place_offset = parts[0] + SPLITTER_REF;
            //STOCKER LA DEUXIEMENT PARTIE DU FRAGMENT DANS UN AUTRE STRING
            place = parts[1];
        }
        //SI LA PLACE NE CONTIENT PAS LE CHARACTERE " OF " STOCKER DANS UN STRING LE STRING PREDEFINI DANS LA RESSOURCE STRING
        // ET STOKER DIRECTEMENT CE STRING DANS PLACE
        else {
            place_offset = getContext().getString(R.string.Near_the);
            place = pojoClass.getPlace();

        }
        TextView place_text_view = (TextView) listItemView.findViewById(R.id.place);
        place_text_view.setText(place);

        TextView offset_text_view = (TextView) listItemView.findViewById(R.id.place_offset);
        offset_text_view.setText(place_offset);

        // **************************************************************************************//


        //RECUPERER ET CONVERTIR LE TIME LONG EN DATE LISIBLE DE FORMAT “2016-05-13 12:07:46 PM”.
        Date date_object = new Date(pojoClass.getDate_millisec()); // RETOURNE UN STRING

        TextView date_text_view = (TextView) listItemView.findViewById(R.id.date);

        // CONVERTIR LA DATE DE HAUT AU FORMAT “Jan 29, 2016”. ET LE STOCKER DANS LA DATE TEXTVIEW
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");  // ON PRECISE LE PATTERN QU'ON VEUT DANS LE CONSTR
        String formattedDate = dateFormat.format(date_object);
        date_text_view.setText(formattedDate);

        // CONVERTIR LA PATIE DES HEURES AU FORMAT "4:30 PM" ET LA STOCKER DANS TIME TEXTVIEW
        TextView time_text_view = (TextView) listItemView.findViewById(R.id.time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
        String formattedTime = timeFormat.format(date_object);
        time_text_view.setText(formattedTime);

        return listItemView;
    }



//.............................ZONE DE CREATION DE METHODES......................................................................................................................//

    // METHODE QUI FORMATE UN DOUBLE EN LAISSANT UNIQUEMENT UN CHIFFRE APRES LA VIRGULE ET RETOURNE UN STRING(LE DOUBLE FORMATÉ)
    private String formatMagnitude(double magnitude){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0"); // PRECISION DU PATTERN A CONSERVER

        //ON APPLIQUE LA BONNE METHODE SUR LE INPUT QUI EST NOTRE DOUBLE EN PARAMETTRE
        return magnitudeFormat.format(magnitude);
    }

    

    //***************************************************************************//

    // METHODE QUI PERMET DE DEFINIR LES DIFFERENTES COULEURS DU BACKGROUND DE LA MAGNITUDE VIEW SELON LA VALEUR DE
    // DE LA MAGNITUDE DEPUIS LA REQUETTE JSON ET RETOURNE UN INT QUI EST LA RESSOURCE DE LA COULEUR SOUHAITÉE

    private int getMagnitudeColor(double magnitude) {
            int magnitudeColorResourceId; // POUR STOCKER LES RESSOURCE DE COULEURS ET LA RETOURNER

            // ENLEVER LES PARTIES DECIMALES DE LA MAGNITUDE EN PARAMETTRE AVANT DE SWITCHER
        // AUSSI LA METHODE SWITCH CASE N'ACCEPTE PAS LES DECIMAUX, D'OU ON CASTE LA MAGNITUDE EN INT AVANT DE LA SWITCHER
        // DU COUP ON A DES CASTS AVEC DES MATH FLOORS........HAAAHHAAAHHAAA
        switch ((int)Math.floor(magnitude)) {
                case 0:
                case 1:
                    magnitudeColorResourceId = R.color.magnitude1;
                    break;
                case 2:
                    magnitudeColorResourceId = R.color.magnitude2;
                    break;
                case 3:
                    magnitudeColorResourceId = R.color.magnitude3;
                    break;
                case 4:
                    magnitudeColorResourceId = R.color.magnitude4;
                    break;
                case 5:
                    magnitudeColorResourceId = R.color.magnitude5;
                    break;
                case 6:
                    magnitudeColorResourceId = R.color.magnitude6;
                    break;
                case 7:
                    magnitudeColorResourceId = R.color.magnitude7;
                    break;
                case 8:
                    magnitudeColorResourceId = R.color.magnitude8;
                    break;
                case 9:
                    magnitudeColorResourceId = R.color.magnitude9;
                    break;
                default:
                    magnitudeColorResourceId = R.color.magnitude10plus;
                    break;
            }

            return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
        }

}