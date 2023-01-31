import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './music.reducer';

export const MusicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const musicEntity = useAppSelector(state => state.music.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="musicDetailsHeading">
          <Translate contentKey="songSocietyApp.music.detail.title">Music</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{musicEntity.id}</dd>
          <dt>
            <span id="songName">
              <Translate contentKey="songSocietyApp.music.songName">Song Name</Translate>
            </span>
          </dt>
          <dd>{musicEntity.songName}</dd>
          <dt>
            <span id="artistName">
              <Translate contentKey="songSocietyApp.music.artistName">Artist Name</Translate>
            </span>
          </dt>
          <dd>{musicEntity.artistName}</dd>
          <dt>
            <span id="albumName">
              <Translate contentKey="songSocietyApp.music.albumName">Album Name</Translate>
            </span>
          </dt>
          <dd>{musicEntity.albumName}</dd>
          <dt>
            <Translate contentKey="songSocietyApp.music.user">User</Translate>
          </dt>
          <dd>
            {musicEntity.users
              ? musicEntity.users.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.login}</a>
                    {musicEntity.users && i === musicEntity.users.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/music" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/music/${musicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MusicDetail;
