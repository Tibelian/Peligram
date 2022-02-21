package com.tibelian.peligram.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tibelian.peligram.database.PostBaseHelper;
import com.tibelian.peligram.database.PostCursorWrapper;
import com.tibelian.peligram.database.PostDbSchema.PostTable;

public class PostLab {

    private static PostLab LABORATORY;
    private Context context;
    private SQLiteDatabase database;

    /**
     * init database connection
     * @param context
     */
    private PostLab(Context context)
    {
        this.context = context.getApplicationContext();
        this.database = new PostBaseHelper(this.context).getWritableDatabase();
    }

    /*
     load singleton object
     */
    public static PostLab get(Context context) {
        if (LABORATORY == null)
            LABORATORY = new PostLab(context);
        return LABORATORY;
    }

    public List<Post> getPosts() {
        // prepare list
        List<Post> posts = new ArrayList<>();
        // find all posts
        PostCursorWrapper cursor = this.queryPosts(null, null);
        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                posts.add(cursor.getPost());
                cursor.moveToNext();
            }
        } finally {
            // close cursor
            cursor.close();
        }
        return posts;
    }

    // allow object insertion
    public void addPost(Post p)
    {
        ContentValues values = this.getContentValues(p);
        database.insert(PostTable.NAME, null, values);
    }

    public Post getPost(UUID id)
    {
        // find by id
        String whereClause = PostTable.Cols.UUID + " = ? ";
        String[] whereArgs = { id.toString() };

        // execute query
        PostCursorWrapper cursor = queryPosts(whereClause, whereArgs);
        try {
            // if not found return null
            if (cursor.getCount() == 0)
                return null;

            // if found then return the object
            cursor.moveToFirst();
            return cursor.getPost();

        } finally {
            // close database cursor
            cursor.close();
        }
    }

    public boolean deletePost(UUID id) {
        // where clause and args
        String whereClause = PostTable.Cols.UUID + " = ? ";
        String[] whereArgs = { id.toString() };

        // delete and obtain the affected rows number
        int n = database.delete(PostTable.NAME, whereClause, whereArgs);
        return ( n != 0 );
    }

    public static ContentValues getContentValues(Post post)
    {
        // creates a container with all post's values
        ContentValues values = new ContentValues();
        values.put(PostTable.Cols.UUID, post.getId().toString());
        values.put(PostTable.Cols.TITLE, post.getTitle());
        values.put(PostTable.Cols.IMAGE, post.getImage());
        values.put(PostTable.Cols.LIKES, post.getLikes());
        return values;
    }

    public void updatePost(Post post)
    {
        // update query
        String uuidString = post.getId().toString();

        // obtain all values of current object
        ContentValues values = getContentValues(post);

        // condition
        String whereClause = PostTable.Cols.UUID + " = ?";
        String[] whereArgs = { uuidString };

        // execute the update query
        database.update(PostTable.NAME, values, whereClause, whereArgs);
    }

    private PostCursorWrapper queryPosts(String whereClause, String[] whereArgs)
    {
        return new PostCursorWrapper(
                database.query(
                        PostTable.NAME, // table name
                        null, // all columns, select *
                        whereClause,
                        whereArgs,
                        null, // group results
                        null, // having
                        null // order results
                )
        );
    }

}
