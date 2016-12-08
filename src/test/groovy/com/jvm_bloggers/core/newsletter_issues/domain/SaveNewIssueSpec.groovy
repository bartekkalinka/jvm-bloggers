package com.jvm_bloggers.core.newsletter_issues.domain

import com.jvm_bloggers.entities.blog_posts.Blog
import com.jvm_bloggers.entities.blog_posts.BlogPost
import com.jvm_bloggers.entities.newsletter_issues.NewsletterIssue

class SaveNewIssueSpec extends NewsletterIssueRepositorySpecBase {

    def "Should save new issue"() {
        given:
            String exampleHeading = "Example heading"
            String exampleVaria = "Example varia"
            long issueNumber = 77L
        and:
            List<Blog> blogs = prepareBlogs()
            List<BlogPost> posts = prepareBlogPosts(blogs.get(0), blogs.get(1))
            NewsletterIssue issue = buildNewsletterIssue(issueNumber, blogs, posts, exampleHeading, exampleVaria)
        when:
            newsletterIssueRepository.save(issue)
        then:
            Optional<NewsletterIssue> persistedIssue =
                    newsletterIssueRepository.findByIssueNumber(issue.getIssueNumber())
            persistedIssue.isPresent()
            with(persistedIssue.get()){
                blogPosts
                issueNumber == issueNumber
                heading == exampleHeading
                varia == exampleVaria
                newBlogs*.id.containsAll(blogs*.id)
                blogPosts*.id.containsAll(posts*.id)
            }
    }
}