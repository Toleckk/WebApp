export interface IPost {
  id?: number;
  text?: string;
  resource?: string;
  ownerLogin?: string;
  ownerId?: number;
}

export const defaultValue: Readonly<IPost> = {};
