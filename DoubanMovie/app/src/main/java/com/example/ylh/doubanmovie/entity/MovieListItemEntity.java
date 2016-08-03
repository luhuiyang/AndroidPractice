package com.example.ylh.doubanmovie.entity;

import java.util.List;

/**
 * Created by ylh on 16-8-2.
 */
public class MovieListItemEntity {

    /**
     * count : 20
     * start : 0
     * total : 2
     * subjects : [{"rating":{"max":10,"average":7.9,"stars":"40","min":0},"genres":["动作","悬疑","科幻"],"title":"我，机器人","casts":[{"alt":"https://movie.douban.com/celebrity/1027138/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/31885.jpg","large":"https://img3.doubanio.com/img/celebrity/large/31885.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/31885.jpg"},"name":"威尔·史密斯","id":"1027138"},{"alt":"https://movie.douban.com/celebrity/1044868/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/23281.jpg","large":"https://img3.doubanio.com/img/celebrity/large/23281.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/23281.jpg"},"name":"布丽姬·穆娜","id":"1044868"},{"alt":"https://movie.douban.com/celebrity/1031855/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/13834.jpg","large":"https://img3.doubanio.com/img/celebrity/large/13834.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/13834.jpg"},"name":"艾伦·图代克","id":"1031855"}],"collect_count":159800,"original_title":"I, Robot","subtype":"movie","directors":[{"alt":"https://movie.douban.com/celebrity/1027346/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/9385.jpg","large":"https://img3.doubanio.com/img/celebrity/large/9385.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/9385.jpg"},"name":"亚历克斯·普罗亚斯","id":"1027346"}],"year":"2004","images":{"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p691069814.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p691069814.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p691069814.jpg"},"alt":"https://movie.douban.com/subject/1308843/","id":"1308843"}]
     * title : 搜索结果
     */

    private int count;
    private int start;
    private int total;
    private String title;
    /**
     * rating : {"max":10,"average":7.9,"stars":"40","min":0}
     * genres : ["动作","悬疑","科幻"]
     * title : 我，机器人
     * casts : [{"alt":"https://movie.douban.com/celebrity/1027138/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/31885.jpg","large":"https://img3.doubanio.com/img/celebrity/large/31885.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/31885.jpg"},"name":"威尔·史密斯","id":"1027138"},{"alt":"https://movie.douban.com/celebrity/1044868/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/23281.jpg","large":"https://img3.doubanio.com/img/celebrity/large/23281.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/23281.jpg"},"name":"布丽姬·穆娜","id":"1044868"},{"alt":"https://movie.douban.com/celebrity/1031855/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/13834.jpg","large":"https://img3.doubanio.com/img/celebrity/large/13834.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/13834.jpg"},"name":"艾伦·图代克","id":"1031855"}]
     * collect_count : 159800
     * original_title : I, Robot
     * subtype : movie
     * directors : [{"alt":"https://movie.douban.com/celebrity/1027346/","avatars":{"small":"https://img3.doubanio.com/img/celebrity/small/9385.jpg","large":"https://img3.doubanio.com/img/celebrity/large/9385.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/9385.jpg"},"name":"亚历克斯·普罗亚斯","id":"1027346"}]
     * year : 2004
     * images : {"small":"https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p691069814.jpg","large":"https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p691069814.jpg","medium":"https://img3.doubanio.com/view/movie_poster_cover/spst/public/p691069814.jpg"}
     * alt : https://movie.douban.com/subject/1308843/
     * id : 1308843
     */

    private List<SubjectsBean> subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SubjectsBean> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectsBean> subjects) {
        this.subjects = subjects;
    }

    public static class SubjectsBean {
        /**
         * max : 10
         * average : 7.9
         * stars : 40
         * min : 0
         */

        private RatingBean rating;
        private String title;
        private int collect_count;
        private String original_title;
        private String subtype;
        private String year;
        /**
         * small : https://img3.doubanio.com/view/movie_poster_cover/ipst/public/p691069814.jpg
         * large : https://img3.doubanio.com/view/movie_poster_cover/lpst/public/p691069814.jpg
         * medium : https://img3.doubanio.com/view/movie_poster_cover/spst/public/p691069814.jpg
         */

        private ImagesBean images;
        private String alt;
        private String id;
        private List<String> genres;
        /**
         * alt : https://movie.douban.com/celebrity/1027138/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/31885.jpg","large":"https://img3.doubanio.com/img/celebrity/large/31885.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/31885.jpg"}
         * name : 威尔·史密斯
         * id : 1027138
         */

        private List<CastsBean> casts;
        /**
         * alt : https://movie.douban.com/celebrity/1027346/
         * avatars : {"small":"https://img3.doubanio.com/img/celebrity/small/9385.jpg","large":"https://img3.doubanio.com/img/celebrity/large/9385.jpg","medium":"https://img3.doubanio.com/img/celebrity/medium/9385.jpg"}
         * name : 亚历克斯·普罗亚斯
         * id : 1027346
         */

        private List<DirectorsBean> directors;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCollect_count() {
            return collect_count;
        }

        public void setCollect_count(int collect_count) {
            this.collect_count = collect_count;
        }

        public String getOriginal_title() {
            return original_title;
        }

        public void setOriginal_title(String original_title) {
            this.original_title = original_title;
        }

        public String getSubtype() {
            return subtype;
        }

        public void setSubtype(String subtype) {
            this.subtype = subtype;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
            this.images = images;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<String> getGenres() {
            return genres;
        }

        public void setGenres(List<String> genres) {
            this.genres = genres;
        }

        public List<CastsBean> getCasts() {
            return casts;
        }

        public void setCasts(List<CastsBean> casts) {
            this.casts = casts;
        }

        public List<DirectorsBean> getDirectors() {
            return directors;
        }

        public void setDirectors(List<DirectorsBean> directors) {
            this.directors = directors;
        }

        public static class RatingBean {
            private int max;
            private double average;
            private String stars;
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public double getAverage() {
                return average;
            }

            public void setAverage(double average) {
                this.average = average;
            }

            public String getStars() {
                return stars;
            }

            public void setStars(String stars) {
                this.stars = stars;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean {
            private String small;
            private String large;
            private String medium;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }
        }

        public static class CastsBean {
            private String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/31885.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/31885.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/31885.jpg
             */

            private AvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }

        public static class DirectorsBean {
            private String alt;
            /**
             * small : https://img3.doubanio.com/img/celebrity/small/9385.jpg
             * large : https://img3.doubanio.com/img/celebrity/large/9385.jpg
             * medium : https://img3.doubanio.com/img/celebrity/medium/9385.jpg
             */

            private AvatarsBean avatars;
            private String name;
            private String id;

            public String getAlt() {
                return alt;
            }

            public void setAlt(String alt) {
                this.alt = alt;
            }

            public AvatarsBean getAvatars() {
                return avatars;
            }

            public void setAvatars(AvatarsBean avatars) {
                this.avatars = avatars;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public static class AvatarsBean {
                private String small;
                private String large;
                private String medium;

                public String getSmall() {
                    return small;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public String getLarge() {
                    return large;
                }

                public void setLarge(String large) {
                    this.large = large;
                }

                public String getMedium() {
                    return medium;
                }

                public void setMedium(String medium) {
                    this.medium = medium;
                }
            }
        }
    }
}
