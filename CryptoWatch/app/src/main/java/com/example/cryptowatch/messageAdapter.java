package com.example.cryptowatch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class messageAdapter extends RecyclerView.Adapter<messageAdapter.CryptoViewHolder> {
    private ArrayList<Message> messages;

    public messageAdapter(ArrayList<Message> messages){
        this.messages = messages;
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_message, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        holder.bind(messages.get(position));


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class CryptoViewHolder extends RecyclerView.ViewHolder{

        public CryptoViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
            });
        }


        public void bind(Message message){
            TextView Tv1 = itemView.findViewById(R.id.sender_name);
            TextView Tv2 = itemView.findViewById(R.id.message_context);
            Tv2.setText(message.com);

            ApiDataBase apiDataBase;
            apiDataBase = RequestBuilder.buildRequest1().create(ApiDataBase.class);
            Call<User> getMessage1 = apiDataBase.getUser(message.usersId);

            getMessage1.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        User users = response.body();
                        Tv1.setText(users.nicknameUsers);
                    }else{
                        Toast.makeText(itemView.getContext(), "Техническая ошибка сервера", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

}
