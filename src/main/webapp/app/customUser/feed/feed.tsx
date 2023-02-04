import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IPost } from '../../shared/model/post.model';

export const Feed = () => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    const fetchPosts = async () => {
      const result = await axios.get('/api/posts/followed');
      setPosts(result.data);
    };
    fetchPosts();
  }, []);

  return (
    <div>
      <h1>Your feed</h1>
      {posts && posts.length > 0 ? (
        <div>
          {posts.map(post => (
            <div key={post.id}>
              <h3>{post.user.login}</h3>
              <p>{post.content}</p>
            </div>
          ))}
        </div>
      ) : (
        <h2> No Posts Found</h2>
      )}
    </div>
  );
};

export default Feed;
