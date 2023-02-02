import dayjs from 'dayjs';
import { ILike } from 'app/shared/model/like.model';
import { IReply } from 'app/shared/model/reply.model';
import { IUser } from 'app/shared/model/user.model';

export interface IPost {
  id?: number;
  date?: string;
  content?: string;
  likes?: ILike[] | null;
  replies?: IReply[] | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IPost> = {};
