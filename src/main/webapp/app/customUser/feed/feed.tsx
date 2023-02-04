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
      {posts.map(post => (
        <div key={post.id}>
          <h3>{post.user.login}</h3>
          <p>{post.content}</p>
        </div>
      ))}
    </div>
  );
};

export default Feed;
