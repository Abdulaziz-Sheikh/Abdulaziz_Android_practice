package Algonquin.cst2335.ui.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface ChatMessageDAO {
    @Insert
    public long insertMessage(ChatMessage m);
    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessage();
    @Delete
    void deleteMessage(ChatMessage m);


}
