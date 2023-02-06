import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IPost } from '../../shared/model/post.model';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IUserProfile } from '../../shared/dto/userProfile.model';

export const Feed = () => {
  const [posts, setPosts] = useState<IPost[]>([]);
  const [followedProfiles, setFollowedProfiles] = useState<IUserProfile[]>([]);

  useEffect(() => {
    const fetchPosts = async () => {
      const result = await axios.get('/api/posts/followed');
      setPosts(result.data);
    };
    fetchPosts();
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
                  {/* <td>{userProfile ? userProfile.societyTag : 'N/A'}</td> */}
                  <td>{userProfile ? <a href={`/profile/${userProfile.id}`}>{userProfile.societyTag}</a> : 'N/A'}</td>
                  <td>{post.content}</td>
                  <td>
                    <Button tag={Link} to={`/post/${post.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                      <FontAwesomeIcon icon="eye" />{' '}
                      <span className="d-none d-md-inline">
                        <Translate contentKey="entity.action.view">Expand</Translate>
                      </span>
                    </Button>
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
  // return (
  //   <table>
  //     <thead>
  //       <tr>
  //         <th>Username</th>
  //         <th>Society Tag</th>
  //         <th>Post</th>
  //       </tr>
  //     </thead>
  //     <tbody>
  //       {posts.map(post => {
  //         const userProfile = followedProfiles.find(
  //           profile => profile.login === post.user.login
  //         );
  //         return (
  //           <tr key={post.id}>
  //             <td>{post.user.login}</td>
  //             <td>{userProfile ? userProfile.societyTag : 'N/A'}</td>
  //             <td>{post.content}</td>
  //           </tr>
  //         );
  //       })}
  //     </tbody>
  //   </table>
  // );
};

export default Feed;
