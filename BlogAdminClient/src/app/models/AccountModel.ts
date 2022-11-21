export interface AccountModel {
    id: number;
    login: string;
    email: string;
    registered: Date;
    quantityPosts: number;
    roleId: number;
    roleName: number;
    isBanned: boolean;
    isDeleted: boolean;
}