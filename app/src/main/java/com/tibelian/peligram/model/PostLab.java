package com.tibelian.peligram.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tibelian.peligram.R;
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

        // sample data
        if (this.getPosts().size() == 0) {
            insertDemoData();
        }
    }

    private void insertDemoData()
    {
        Post p0 = new Post();
        p0.setTitle("Karate kid");
        p0.setImage(R.drawable.movie0);
        p0.setLikes(5);
        Post p1 = new Post();
        p1.setTitle("Avengers");
        p1.setImage(R.drawable.movie1);
        p1.setLikes(8);
        Post p2 = new Post();
        p2.setTitle("Avatar");
        p2.setImage(R.drawable.movie2);
        p2.setLikes(3);
        Post p3 = new Post();
        p3.setTitle("Spiderman");
        p3.setImage(R.drawable.movie3);
        p3.setLikes(12);
        Post p4 = new Post();
        p4.setTitle("Dunkerque");
        p4.setImage(R.drawable.movie4);
        p4.setLikes(1);
        Post p5 = new Post();
        p5.setTitle("Divergent");
        p5.setImage(R.drawable.movie5);
        p5.setLikes(4);
        Post p6 = new Post();
        p6.setTitle("Jackie Chan");
        p6.setImage(R.drawable.movie6);
        p6.setLikes(2);
        Post p7 = new Post();
        p7.setTitle("Ready Player One");
        p7.setImage(R.drawable.movie7);
        p7.setLikes(8);
        Post p8 = new Post();
        p8.setTitle("Toy Story");
        p8.setImage(R.drawable.movie8);
        p8.setLikes(4);
        Post p9 = new Post();
        p9.setTitle("Jurassic Park");
        p9.setImage(R.drawable.movie9);
        p9.setLikes(6);
        // save into database
        addPost(p0);
        addPost(p1);
        addPost(p2);
        addPost(p3);
        addPost(p4);
        addPost(p5);
        addPost(p6);
        addPost(p7);
        addPost(p8);
        addPost(p9);
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

    public List<Post> getPopularPosts(int limit) {
        // prepare list
        List<Post> posts = new ArrayList<>();
        // find all posts and order by likes
        String orderBy = PostTable.Cols.LIKES + " DESC";
        PostCursorWrapper cursor = this.queryPosts(null, null, null, null, orderBy, "" + limit);
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

    private PostCursorWrapper queryPosts(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, String limit)
    {
        return new PostCursorWrapper(
                database.query(
                        PostTable.NAME, // table name
                        null, // all columns, select *
                        whereClause,
                        whereArgs,
                        groupBy,
                        having,
                        orderBy,
                        limit
                )
        );
    }

    private PostCursorWrapper queryPosts(String whereClause, String[] whereArgs)
    {
        return queryPosts(whereClause, whereArgs, null, null, null, null);
    }

}
