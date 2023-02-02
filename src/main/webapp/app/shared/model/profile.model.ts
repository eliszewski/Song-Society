import { IUser } from 'app/shared/model/user.model';

export interface IProfile {
  id?: number;
  societyTag?: string;
  profilePictureContentType?: string | null;
  profilePicture?: string | null;
  spotifyToken?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<IProfile> = {};
