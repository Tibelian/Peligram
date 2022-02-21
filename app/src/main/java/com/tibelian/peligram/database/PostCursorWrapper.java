package com.tibelian.peligram.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;
import com.tibelian.peligram.database.PostDbSchema.PostTable;
import com.tibelian.peligram.model.Post;

public class PostCursorWrapper extends CursorWrapper {

    public PostCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    // creates the post object
    public Post getPost() {

        String uuidString = getString(getColumnIndex(PostTable.Cols.UUID));
        String title = getString(getColumnIndex(PostTable.Cols.TITLE));
        String image = getString(getColumnIndex(PostTable.Cols.IMAGE));
        int likes = getInt(getColumnIndex(PostTable.Cols.LIKES));

        Post p = new Post(UUID.fromString(uuidString));
        p.setTitle(title);
        p.setImage(image);
        p.setLikes(likes);

        return p;

    }
}
