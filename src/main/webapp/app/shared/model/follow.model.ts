import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IFollow {
  id?: number;
  date?: string;
  follower?: IUser | null;
  followed?: IUser | null;
}

export const defaultValue: Readonly<IFollow> = {};
