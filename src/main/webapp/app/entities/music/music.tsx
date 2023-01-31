import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMusic } from 'app/shared/model/music.model';
import { getEntities } from './music.reducer';

export const Music = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const musicList = useAppSelector(state => state.music.entities);
  const loading = useAppSelector(state => state.music.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="music-heading" data-cy="MusicHeading">
        <Translate contentKey="songSocietyApp.music.home.title">Music</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="songSocietyApp.music.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/music/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="songSocietyApp.music.home.createLabel">Create new Music</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {musicList && musicList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="songSocietyApp.music.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.music.songName">Song Name</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.music.artistName">Artist Name</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.music.albumName">Album Name</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.music.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {musicList.map((music, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/music/${music.id}`} color="link" size="sm">
                      {music.id}
                    </Button>
                  </td>
                  <td>{music.songName}</td>
                  <td>{music.artistName}</td>
                  <td>{music.albumName}</td>
                  <td>
                    {music.users
                      ? music.users.map((val, j) => (
                          <span key={j}>
                            {val.login}
                            {j === music.users.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/music/${music.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/music/${music.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/music/${music.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="songSocietyApp.music.home.notFound">No Music found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Music;
