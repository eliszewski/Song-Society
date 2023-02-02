import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Music from './music';
import MusicDetail from './music-detail';
import MusicUpdate from './music-update';
import MusicDeleteDialog from './music-delete-dialog';

const MusicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Music />} />
    <Route path="new" element={<MusicUpdate />} />
    <Route path=":id">
      <Route index element={<MusicDetail />} />
      <Route path="edit" element={<MusicUpdate />} />
      <Route path="delete" element={<MusicDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MusicRoutes;
