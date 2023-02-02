import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';
import { IPost } from 'app/shared/model/post.model';

export interface IReply {
  id?: number;
  date?: string;
  content?: string;
  user?: IUser | null;
  post?: IPost | null;
}

export const defaultValue: Readonly<IReply> = {};
