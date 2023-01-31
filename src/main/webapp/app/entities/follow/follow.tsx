import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFollow } from 'app/shared/model/follow.model';
import { getEntities } from './follow.reducer';

export const Follow = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const followList = useAppSelector(state => state.follow.entities);
  const loading = useAppSelector(state => state.follow.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="follow-heading" data-cy="FollowHeading">
        <Translate contentKey="songSocietyApp.follow.home.title">Follows</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="songSocietyApp.follow.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/follow/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="songSocietyApp.follow.home.createLabel">Create new Follow</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {followList && followList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="songSocietyApp.follow.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.follow.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.follow.follower">Follower</Translate>
                </th>
                <th>
                  <Translate contentKey="songSocietyApp.follow.followed">Followed</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {followList.map((follow, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/follow/${follow.id}`} color="link" size="sm">
                      {follow.id}
                    </Button>
                  </td>
                  <td>{follow.date ? <TextFormat type="date" value={follow.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{follow.follower ? follow.follower.login : ''}</td>
                  <td>{follow.followed ? follow.followed.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/follow/${follow.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/follow/${follow.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/follow/${follow.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="songSocietyApp.follow.home.notFound">No Follows found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Follow;
