import { Comment } from "./comment.interface";

export interface Post {
    id?: number;
    title: string;

    created_at: string;
    author_id: number;
    topic_id: number;
    content?: string;
    comments?: Comment[]
}

export interface PostResponse {
    content: Post[];
    pageable: any;
    totalElements: number;
    totalPages: number;
}