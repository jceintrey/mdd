export interface Comment {
    id: number;
    content: string;
    created_at: Date;
    author_id: number;
    post_id: number;
}