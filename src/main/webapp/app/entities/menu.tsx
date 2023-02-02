import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/profile">
        <Translate contentKey="global.menu.entities.profile" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/post">
        <Translate contentKey="global.menu.entities.post" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/like">
        <Translate contentKey="global.menu.entities.like" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/follow">
        <Translate contentKey="global.menu.entities.follow" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/reply">
        <Translate contentKey="global.menu.entities.reply" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/music">
        <Translate contentKey="global.menu.entities.music" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
