import { author } from "./author.interface";
import { Comment } from "./comment.interface";
import { Topic } from "./topic.interface";

export interface Post {
    id?: number;
    title: string;

    created_at: string;
    author: author;
    topic: Topic;
    content?: string;
    comments?: Comment[]
}

export interface PostResponse {
    content: Post[];
    pageable: any;
    totalElements: number;
    totalPages: number;
}