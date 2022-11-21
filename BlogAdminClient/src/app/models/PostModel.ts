
export interface PostModel {
    postId: number;
    accountId: number;
    categoryId: number;
    statusId: number;
    title: string;
    anons: string;
    fullText: string;
    lastChange: Date;
    statusName: string;
    accountLogin: string;
    categoryName: string;
}
