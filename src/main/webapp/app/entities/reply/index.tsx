import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Reply from './reply';
import ReplyDetail from './reply-detail';
import ReplyUpdate from './reply-update';
import ReplyDeleteDialog from './reply-delete-dialog';

const ReplyRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Reply />} />
    <Route path="new" element={<ReplyUpdate />} />
    <Route path=":id">
      <Route index element={<ReplyDetail />} />
      <Route path="edit" element={<ReplyUpdate />} />
      <Route path="delete" element={<ReplyDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ReplyRoutes;
