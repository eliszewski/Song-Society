import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Button } from 'reactstrap';

interface Props {
  postId: number;
}

const LikeButton: React.FC<Props> = ({ postId }) => {
  const [hasLiked, setHasLiked] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [userId, setUserId] = useState<number | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      const result = await axios.get('/api/account');
      setUserId(result.data.id);
    };
    fetchUser();
  }, []);

  useEffect(() => {
    const fetchLike = async () => {
      if (!userId) {
        return;
      }
      setIsLoading(true);
      const result = await axios.get(`/api/likes?userId=${userId}&postId=${postId}`);
      setHasLiked(result.data.length > 0);
      setIsLoading(false);
    };
    fetchLike();
  }, [userId, postId]);

  const handleLikeClick = async () => {
    if (!userId) {
      return;
    }
    setIsLoading(true);
    if (hasLiked) {
      await axios.delete(`/api/likes/${userId}/${postId}`);
    } else {
      await axios.post('/api/likes', { userId, postId });
    }
    setHasLiked(!hasLiked);
    setIsLoading(false);
  };

  return (
    <Button color={hasLiked ? 'danger' : 'secondary'} onClick={handleLikeClick} disabled={isLoading}>
      {hasLiked ? 'Unlike' : 'Like'}
    </Button>
  );
};

export default LikeButton;
