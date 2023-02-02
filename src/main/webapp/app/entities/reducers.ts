import profile from 'app/entities/profile/profile.reducer';
import post from 'app/entities/post/post.reducer';
import like from 'app/entities/like/like.reducer';
import follow from 'app/entities/follow/follow.reducer';
import reply from 'app/entities/reply/reply.reducer';
import music from 'app/entities/music/music.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  profile,
  post,
  like,
  follow,
  reply,
  music,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
