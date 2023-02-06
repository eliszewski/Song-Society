import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { IPost } from '../../shared/model/post.model';
import { Button, Table } from 'reactstrap';
import { TextFormat, Translate } from 'react-jhipster';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const Feed = () => {
  const [posts, setPosts] = useState<IPost[]>([]);

  useEffect(() => {
    const fetchPosts = async () => {
      const result = await axios.get('/api/posts/followed');
      setPosts(result.data);
    };
    fetchPosts();
  }, []);

  // return (
  //   <div>
  //     <h1>Your feed</h1>
  //     {posts && posts.length > 0 ? (
  //       <div>
  //         {posts.map(post => (
  //           <div key={post.id}>
  //             <h3>{post.user.login}</h3>
  //             <p>{post.content}</p>
  //           </div>
  //         ))}
  //       </div>
  //     ) : (
  //       <h2> No Posts Found</h2>
  //     )}
  //   </div>
  // );

  return (
    <div>
      <h1>Your feed</h1>
      {posts && posts.length > 0 ? (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Date</th>
              <th>Username</th>
              <th>Post</th>
            </tr>
          </thead>
          <tbody>
            {posts.map(post => (
              <tr key={post.id}>
                <td>{post.date ? <TextFormat type="date" value={post.date} format={APP_DATE_FORMAT} /> : null}</td>
                <td>{post.user.login}</td>
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
            ))}
          </tbody>
        </Table>
      ) : (
        <h2>No Posts Found</h2>
      )}
    </div>
  );
};

export default Feed;
