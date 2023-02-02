import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Follow from './follow';
import FollowDetail from './follow-detail';
import FollowUpdate from './follow-update';
import FollowDeleteDialog from './follow-delete-dialog';

const FollowRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Follow />} />
    <Route path="new" element={<FollowUpdate />} />
    <Route path=":id">
      <Route index element={<FollowDetail />} />
      <Route path="edit" element={<FollowUpdate />} />
      <Route path="delete" element={<FollowDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FollowRoutes;
