import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Profile from './profile';
import Post from './post';
import Like from './like';
import Follow from './follow';
import Reply from './reply';
import Music from './music';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="profile/*" element={<Profile />} />
        <Route path="post/*" element={<Post />} />
        <Route path="like/*" element={<Like />} />
        <Route path="follow/*" element={<Follow />} />
        <Route path="reply/*" element={<Reply />} />
        <Route path="music/*" element={<Music />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
