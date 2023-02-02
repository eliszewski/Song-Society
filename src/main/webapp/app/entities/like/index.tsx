import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Like from './like';
import LikeDetail from './like-detail';
import LikeUpdate from './like-update';
import LikeDeleteDialog from './like-delete-dialog';

const LikeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Like />} />
    <Route path="new" element={<LikeUpdate />} />
    <Route path=":id">
      <Route index element={<LikeDetail />} />
      <Route path="edit" element={<LikeUpdate />} />
      <Route path="delete" element={<LikeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LikeRoutes;
