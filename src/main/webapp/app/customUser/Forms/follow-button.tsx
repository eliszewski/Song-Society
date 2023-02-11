import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IUser } from 'app/shared/model/user.model';
import { IFollow } from 'app/shared/model/follow.model';

interface FollowButtonProps {
  followedUser: IUser;
  currentUser: IUser;
}

const FollowButton: React.FC<FollowButtonProps> = ({ followedUser, currentUser }) => {
  const [following, setFollowing] = useState(false);

  useEffect(() => {
    const fetchFollowStatus = async () => {
      const result = await axios.get(`/api/follows?follower=${currentUser}&followed=${followedUser}`);
      setFollowing(result.data.length > 0);
    };
    fetchFollowStatus();
  }, [currentUser, followedUser]);

  const handleFollow = async () => {
    const follow: IFollow = {
      date: new Date().toISOString(),
      follower: currentUser,
      followed: followedUser,
    };
    try {
      await axios.post('/api/follows', follow);
      setFollowing(true);
    } catch (error) {
      console.error(error);
    }
  };

  const handleUnfollow = async () => {
    try {
      const result = await axios.get(`/api/follows?follower=${currentUser}&followed=${followedUser}`);
      await axios.delete(`/api/follows/${result.data[0].id}`);
      setFollowing(false);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      {following ? (
        <button onClick={handleUnfollow}>Unfollow</button>
      ) : (
        <button onClick={handleFollow}>Follow</button>
      )}
    </>
  );
};

export default FollowButton;
