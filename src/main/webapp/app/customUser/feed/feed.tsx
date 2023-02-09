import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IPost } from '../../shared/model/post.model';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IUserProfile } from '../../shared/dto/userProfile.model';
import ReplyButton from '../Forms/reply-button';
import authentication, { authenticate } from '../../shared/reducers/authentication';
import { IUser } from 'app/shared/model/user.model';
import { getAccount } from '../../shared/reducers/authentication';
import PostButton from '../Forms/new-post-botton';

export const Feed = () => {
  const [posts, setPosts] = useState<IPost[]>([]);
  const [followedProfiles, setFollowedProfiles] = useState<IUserProfile[]>([]);
  const [user, setUser] = useState<IUser>(null);

  useEffect(() => {
    const fetchPosts = async () => {
      const result = await axios.get('/api/posts/followed');
      setPosts(result.data);
    };
    fetchPosts();
    const fetchUser = async () => {
      const result = await axios.get('/api/account');
      setUser(result.data);
    };
    fetchUser();
  }, []);

  useEffect(() => {
    const fetchProfiles = async () => {
      const result = await axios.get('/api/profiles/followed-tags');
      setFollowedProfiles(result.data);
    };
    fetchProfiles();
  }, []);

  return (
    <div>
      <h1>Your feed</h1>
      <span>
        <PostButton author={user}></PostButton>
      </span>
      {posts && posts.length > 0 ? (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Posted By</th>
              <th>Post</th>
            </tr>
          </thead>
          <tbody>
            {posts.map(post => {
              const userProfile = followedProfiles.find(profile => profile.login === post.user.login);
              return (
                <tr key={post.id}>
                  <td>{post.date ? <TextFormat type="date" value={post.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{userProfile ? <a href={`/profile/${userProfile.id}`}>{userProfile.societyTag}</a> : 'User not found'}</td>
                  <td>{post.content}</td>
                  <td>
                    <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">Expand</span>
                    </Button>
                    &nbsp;
                    <ReplyButton post={post} currentUser={user}></ReplyButton>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </Table>
      ) : (
        <h2>No Posts Found</h2>
      )}
    </div>
  );
};

export default Feed;
