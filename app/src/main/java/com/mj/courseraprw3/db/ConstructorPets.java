package com.mj.courseraprw3.db;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.mj.courseraprw3.R;
import com.mj.courseraprw3.pojo.likes;
import com.mj.courseraprw3.pojo.pets;
import com.mj.courseraprw3.restAPI.Adapter.RestApiAdapter;
import com.mj.courseraprw3.restAPI.EndpointsApi;
import com.mj.courseraprw3.restAPI.model.LikeSetResponse;
import com.mj.courseraprw3.restAPI.model.LikesResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by leyenda1 on 01/10/2016.
 */

public class ConstructorPets {
    private static final int LIKE = 1;
    Context context;
    private ArrayList<likes> myLikeNumber;

    public ConstructorPets(Context context) {
        this.context = context;
    }

    public ArrayList<pets> obtenerDatos (){
        ArrayList<pets> myDBPets;
        ArrayList<pets> myPetList = new ArrayList<>();
        int myIndx=0;

   /*     BaseDatos db = new BaseDatos(context);

        myPetList.add(new pets(1,"Beary",0, R.drawable.bear));
        myPetList.add(new pets(2,"Beavery",0, R.drawable.beaver));
        myPetList.add(new pets(3,"Caty",0, R.drawable.cat));
        myPetList.add(new pets(4,"Cowy",0, R.drawable.cow));
        myPetList.add(new pets(5,"Doggy",0, R.drawable.dog));
        myPetList.add(new pets(6,"Elephanty",0, R.drawable.elephant));
        myPetList.add(new pets(7,"Goaty",0, R.drawable.goat));
        myPetList.add(new pets(8,"Horsy",0, R.drawable.horse));
        myPetList.add(new pets(9,"Jiraffy",0, R.drawable.jiraff));
        myPetList.add(new pets(10,"Lamby",0, R.drawable.lamb));
        myPetList.add(new pets(11,"Monkey",0, R.drawable.monkey));
        myPetList.add(new pets(12,"Moosy",0, R.drawable.moose));
        myPetList.add(new pets(13,"Owly",0, R.drawable.owl));
        myPetList.add(new pets(14,"Pandy",0, R.drawable.panda));
        myPetList.add(new pets(15,"Piggy",0, R.drawable.pig));
        myPetList.add(new pets(16,"Ramy",0, R.drawable.ram));
        myPetList.add(new pets(17,"Rhiny",0, R.drawable.rhino));
        myPetList.add(new pets(18,"Tiggy",0, R.drawable.tiger));
        myPetList.add(new pets(19,"Turkky",0, R.drawable.turkey));
        myPetList.add(new pets(20,"Zebry",0, R.drawable.zebra));



        myDBPets = db.getMyPetList();

        for(int i = 0 ; i <  myDBPets.size() ; i++) {
            for(int j=0 ; j < myPetList.size() ; j++){
                if(myDBPets.get(i).getId() == myPetList.get(j).getId()){
                    myPetList.get(j).setLikes(myDBPets.get(i).getLikes());
                    break;
                }
            }
        }
     */
        return myPetList;
    }

    public void giveLike (pets Pet) {
        BaseDatos db = new BaseDatos(context);
        ArrayList<pets> myTmpPets;
/*
        myTmpPets = db.getMyPetList();

        for (int i = 0; i < myTmpPets.size(); i++) {
            if(Pet.getId() == myTmpPets.get(i).getId()){
                insertlikes(Pet);
                return;
            }
        }

        if(myTmpPets.size() == 5) {
            pets tmpPet = myTmpPets.get(myTmpPets.size() - 1);
            db.deletePet(tmpPet.getId());
            db.deletePetLikes(tmpPet.getId());
        }

        insertMyPet(Pet);
        insertlikes(Pet);
        */
    }

    public void insertMyPet(pets Pet){
    /*    BaseDatos db = new BaseDatos(context);

        ContentValues myNewPet = new ContentValues();
        myNewPet.put(ConstantesBD.TABLE_PETS_ID, Pet.getId());
        myNewPet.put(ConstantesBD.TABLE_PETS_NAME, Pet.getName());
        myNewPet.put(ConstantesBD.TABLE_PETS_PICTURE, Pet.getPicture());
        db.insertPet(myNewPet);
        */
    }

    public void insertlikes(pets Pet) {
     /*   BaseDatos db = new BaseDatos(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put(ConstantesBD.TABLE_PETS_LIKES_ID_PETS, Pet.getId());
        contentValues.put(ConstantesBD.TABLE_PETS_LIKES_NUMERO_LIKES, LIKE );
        db.insertLikePet(contentValues);  */
    }

    public int gatherPetLikes(pets Pet){
        RestApiAdapter restApiAdapter = new RestApiAdapter();
        Gson gsonLikes = restApiAdapter.buildGsonDeserializarlikes();
        EndpointsApi endpoints = restApiAdapter.stablishConnectionApiInstagram(gsonLikes);
        Call<LikesResponse> likesResponseCall = endpoints.getMyLikeNumber(Pet.getId());

        likesResponseCall.enqueue(new Callback<LikesResponse>() {
            @Override
            public void onResponse(Call<LikesResponse> call, Response<LikesResponse> response) {
                if(response.body() != null) {
                    LikesResponse likesResponse = response.body();
                    setMyLikeNumber(likesResponse.getMylikes());
                    Log.d("MY_LIKE_COUNT_SUCCESS", "SUCCESS: "  +  response.body().getMylikes().size());
                }  else {
                    Log.d("MY_LIKE_COUNT_ERROR", "FAILED: "  +  response.raw());
                }

            }

            @Override
            public void onFailure(Call<LikesResponse> call, Throwable t) {

            }
        });

        /*
        BaseDatos db = new BaseDatos(context);
        return db.getLikesPet(Pet);
        */
        return 0;
    }

    public void setMyLikeNumber (ArrayList<likes> myResponse) {
        myLikeNumber = new ArrayList<>();

        for (int i = 0; i < myResponse.size() ; i++) {
            likes tmpRes = new likes();

            tmpRes.setId(myResponse.get(i).getId());
            tmpRes.setFirst_name(myResponse.get(i).getFirst_name());
            tmpRes.setLast_name(myResponse.get(i).getLast_name());
            tmpRes.setType(myResponse.get(i).getType());

            myLikeNumber.add(tmpRes);
        }
    }

}
