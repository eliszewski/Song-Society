import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './post.reducer';
import { useState } from 'react';
import { IProfile } from '../../shared/model/profile.model';
import axios from 'axios';
import './post-buttons.scss';
import { ILike } from '../../shared/model/like.model';
import { IUser } from '../../shared/model/user.model';
import post from '.';
import { IPost } from 'app/shared/model/post.model';
import ReplyButton from 'app/customUser/Forms/reply-button';

export const PostDetail = () => {
  const [author, setAuthor] = useState<IProfile>(null);
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  const [likes, setLikes] = useState<ILike[]>([]);
  const [user, setUser] = useState<IUser>();

  const createLike = async (user: IUser, post: IPost): Promise<ILike> => {
    const date = new Date().toISOString();
    const like: ILike = { date, user, post };
    try {
      const response = await axios.post('/api/likes', like);
      return response.data;
    } catch (error) {
      console.error(error);
      throw error;
    }
  };

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  useEffect(() => {
    const fetchProfile = async (id: number) => {
      const result = await axios.get(`/api/profiles/post/{id}?id=${id}`);
      setAuthor(result.data);
    };
    fetchProfile(Number(id));
  }, []);

  useEffect(() => {
    const fetchLikes = async (id: number) => {
      const result = await axios.get(`/api//likes/post/{id}?id=${id}`);
      setLikes(result.data);
    };
    fetchLikes(Number(id));
  }, []);

  useEffect(() => {
    const fetchUser = async () => {
      const result = await axios.get(`/api//account`);
      setUser(result.data);
    };
    fetchUser();
  }, []);

  const postEntity = useAppSelector(state => state.post.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="postDetailsHeading">
          <Translate contentKey="songSocietyApp.post.detail.title">Post</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="songSocietyApp.post.date">Date</Translate>
            </span>
          </dt>
          <dd>{postEntity.date ? <TextFormat value={postEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="songSocietyApp.post.content">Content</Translate>
            </span>
          </dt>
          <dd>{postEntity.content}</dd>
          <dt>
            <Translate contentKey="songSocietyApp.post.user">User</Translate>
          </dt>
          <dd>{author ? <a href={`/profile/${author.id}`}>{author.societyTag}</a> : ''}</dd>
        </dl>
        <Button tag={Link} to="/feed" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/post/${postEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
        &nbsp;
        <Button className="like-button-red" onClick={() => createLike(user, postEntity)}>
          <FontAwesomeIcon icon="heart" color="red" /> <span className="button-text">{likes ? likes.length : '0'}</span>
        </Button>
        &nbsp;
        <ReplyButton post={postEntity} currentUser={user}></ReplyButton>
      </Col>
    </Row>
  );
};

export default PostDetail;
