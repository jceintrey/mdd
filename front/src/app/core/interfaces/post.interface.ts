export interface Post {
    id?: number;
    title: string;

    created_at: string;
    author_id: number;
    topic_id: number;
    extract?: string;
}

export interface PostResponse {
    content: Post[];
    pageable: any;
    totalElements: number;
    totalPages: number;
}