import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './reply.reducer';

export const ReplyDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const replyEntity = useAppSelector(state => state.reply.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="replyDetailsHeading">
          <Translate contentKey="songSocietyApp.reply.detail.title">Reply</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{replyEntity.id}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="songSocietyApp.reply.date">Date</Translate>
            </span>
          </dt>
          <dd>{replyEntity.date ? <TextFormat value={replyEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="songSocietyApp.reply.content">Content</Translate>
            </span>
          </dt>
          <dd>{replyEntity.content}</dd>
          <dt>
            <Translate contentKey="songSocietyApp.reply.user">User</Translate>
          </dt>
          <dd>{replyEntity.user ? replyEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="songSocietyApp.reply.post">Post</Translate>
          </dt>
          <dd>{replyEntity.post ? replyEntity.post.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/reply" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reply/${replyEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ReplyDetail;
