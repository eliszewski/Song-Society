import { IUser } from 'app/shared/model/user.model';

export interface IMusic {
  id?: number;
  songName?: string;
  artistName?: string;
  albumName?: string | null;
  users?: IUser[] | null;
}

export const defaultValue: Readonly<IMusic> = {};
