"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Badge } from "@/components/ui/badge"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogTrigger } from "@/components/ui/dialog"
import { ScrollArea } from "@/components/ui/scroll-area"
import { Search, Star, GitFork, MapPin, Building, Link, Trash2, Github, Info, Mail, ExternalLink } from "lucide-react"

interface GitHubUser {
  login: string
  name: string
  avatar_url: string
  bio: string
  public_repos: number
  followers: number
  following: number
  location: string
  blog: string
  company: string
  created_at: string
  html_url: string
}

interface GitHubRepo {
  id: number
  name: string
  description: string
  html_url: string
  stargazers_count: number
  forks_count: number
  language: string
  updated_at: string
}

export default function GitHubProfileExplorer() {
  const [searchTerm, setSearchTerm] = useState("")
  const [user, setUser] = useState<GitHubUser | null>(null)
  const [repos, setRepos] = useState<GitHubRepo[]>([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")
  const [searchHistory, setSearchHistory] = useState<string[]>([])

  const searchUser = async () => {
    if (!searchTerm.trim()) return

    setLoading(true)
    setError("")

    try {
      // Fetch user data
      const userResponse = await fetch(`https://api.github.com/users/${searchTerm}`)
      if (!userResponse.ok) {
        throw new Error("User not found")
      }
      const userData = await userResponse.json()
      setUser(userData)

      // Fetch user repositories
      const reposResponse = await fetch(`https://api.github.com/users/${searchTerm}/repos?sort=updated&per_page=6`)
      const reposData = await reposResponse.json()
      setRepos(reposData)

      // Add to search history
      setSearchHistory((prev) => {
        const newHistory = [searchTerm, ...prev.filter((term) => term !== searchTerm)]
        return newHistory.slice(0, 10)
      })
    } catch (err) {
      setError("User not found or API error")
      setUser(null)
      setRepos([])
    } finally {
      setLoading(false)
    }
  }

  const handleHistoryClick = (username: string) => {
    setSearchTerm(username)
    // Auto search when clicking history item
    setTimeout(() => {
      const event = { target: { value: username } } as any
      setSearchTerm(username)
      searchUser()
    }, 100)
  }

  const deleteFromHistory = (usernameToDelete: string, e: React.MouseEvent) => {
    e.stopPropagation() // Prevent triggering the search
    setSearchHistory((prev) => prev.filter((username) => username !== usernameToDelete))
  }

  const clearAllHistory = () => {
    setSearchHistory([])
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-900 via-blue-900 to-indigo-900">
      <div className="container mx-auto px-6 py-12">
        {/* Header with About Button */}
        <div className="relative">
          {/* About Button - Top Right */}
          <div className="absolute top-0 right-0">
            <Dialog>
              <DialogTrigger asChild>
                <Button
                  variant="ghost"
                  size="sm"
                  className="text-purple-200 hover:text-white hover:bg-purple-700/50 flex items-center gap-2"
                >
                  <Info className="w-4 h-4" />
                  About
                </Button>
              </DialogTrigger>
              <DialogContent className="bg-purple-900/95 border-purple-600 text-white max-w-2xl max-h-[80vh] p-0">
                <DialogHeader className="p-6 pb-0">
                  <DialogTitle className="text-2xl font-bold text-white flex items-center gap-2">
                    <Github className="w-6 h-6" />
                    About GitHub Profile Explorer
                  </DialogTitle>
                </DialogHeader>

                <ScrollArea className="h-[60vh] px-6">
                  <div className="space-y-6 pb-6">
                    {/* Project Aim */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">🎯 Project Main Aim</h3>
                      <p className="text-gray-300 leading-relaxed">
                        GitHub Profile Explorer is designed to provide a beautiful and intuitive way to discover and
                        explore GitHub profiles. The main goal is to make it easy for developers, recruiters, and tech
                        enthusiasts to quickly view comprehensive information about GitHub users, including their
                        repositories, statistics, and profile details in a visually appealing interface.
                      </p>
                      <p className="text-gray-300 leading-relaxed mt-3">
                        This application bridges the gap between GitHub's raw data and user-friendly presentation,
                        making it easier to evaluate developers' work, discover interesting projects, and connect with
                        the open-source community.
                      </p>
                    </div>

                    {/* Tech Stack */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">🛠️ Technology Stack</h3>
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                        <div className="space-y-2">
                          <h4 className="font-medium text-white">Frontend Technologies</h4>
                          <div className="flex flex-wrap gap-2">
                            <Badge className="bg-blue-600">React 18</Badge>
                            <Badge className="bg-black">Next.js 14</Badge>
                            <Badge className="bg-blue-500">TypeScript</Badge>
                            <Badge className="bg-cyan-600">Tailwind CSS</Badge>
                          </div>
                        </div>
                        <div className="space-y-2">
                          <h4 className="font-medium text-white">Key Features</h4>
                          <div className="flex flex-wrap gap-2">
                            <Badge className="bg-green-600">GitHub API</Badge>
                            <Badge className="bg-purple-600">Responsive Design</Badge>
                            <Badge className="bg-orange-600">Search History</Badge>
                            <Badge className="bg-pink-600">Real-time Data</Badge>
                          </div>
                        </div>
                      </div>
                      <div className="mt-4">
                        <h4 className="font-medium text-white mb-2">Additional Libraries</h4>
                        <div className="flex flex-wrap gap-2">
                          <Badge className="bg-gray-600">Lucide React Icons</Badge>
                          <Badge className="bg-indigo-600">Radix UI Components</Badge>
                          <Badge className="bg-teal-600">CSS Animations</Badge>
                          <Badge className="bg-red-600">Error Handling</Badge>
                        </div>
                      </div>
                    </div>

                    {/* Features */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">✨ Key Features</h3>
                      <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                        <div className="bg-purple-800/30 rounded-lg p-3">
                          <h4 className="font-medium text-white mb-1">🔍 Advanced Search</h4>
                          <p className="text-purple-200 text-sm">Real-time GitHub user search with error handling</p>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3">
                          <h4 className="font-medium text-white mb-1">📊 Profile Analytics</h4>
                          <p className="text-purple-200 text-sm">Comprehensive user statistics and information</p>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3">
                          <h4 className="font-medium text-white mb-1">📚 Repository Showcase</h4>
                          <p className="text-purple-200 text-sm">Latest repositories with detailed information</p>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3">
                          <h4 className="font-medium text-white mb-1">🕒 Search History</h4>
                          <p className="text-purple-200 text-sm">Track and revisit previous searches easily</p>
                        </div>
                      </div>
                    </div>

                    {/* Developer Details */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">👨‍💻 Developer Information</h3>
                      <div className="bg-purple-800/50 rounded-lg p-4 space-y-4">
                        <div className="flex items-center gap-3">
                          <div className="w-16 h-16 bg-gradient-to-br from-purple-600 to-blue-600 rounded-full flex items-center justify-center">
                            <span className="text-white font-bold text-xl">CV</span>
                          </div>
                          <div>
                            <h4 className="font-semibold text-white text-lg">Chanumolu Vamsi</h4>
                            <p className="text-purple-200">Full Stack Developer & Software Engineer</p>
                            <p className="text-purple-300 text-sm">
                              Passionate about creating innovative web solutions
                            </p>
                          </div>
                        </div>

                        <div className="grid grid-cols-1 md:grid-cols-2 gap-3">
                          <div className="space-y-2">
                            <div className="flex items-center gap-2 text-purple-200">
                              <Mail className="w-4 h-4" />
                              <a
                                href="mailto:vamsichanumolu72@gmail.com"
                                className="hover:text-white transition-colors"
                              >
                                vamsichanumolu72@gmail.com
                              </a>
                            </div>
                            <div className="flex items-center gap-2 text-purple-200">
                              <Github className="w-4 h-4" />
                              <a
                                href="https://github.com/vamsichanumolu"
                                target="_blank"
                                rel="noopener noreferrer"
                                className="hover:text-white transition-colors flex items-center gap-1"
                              >
                                GitHub Profile
                                <ExternalLink className="w-3 h-3" />
                              </a>
                            </div>
                          </div>
                          <div className="space-y-2">
                            <div className="text-purple-200">
                              <span className="font-medium">Specializations:</span>
                              <div className="flex flex-wrap gap-1 mt-1">
                                <Badge className="bg-blue-600 text-xs">React</Badge>
                                <Badge className="bg-green-600 text-xs">Node.js</Badge>
                                <Badge className="bg-purple-600 text-xs">TypeScript</Badge>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>

                    {/* Project Stats */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">📈 Project Statistics</h3>
                      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                        <div className="bg-purple-800/30 rounded-lg p-3 text-center">
                          <div className="text-2xl font-bold text-white">100%</div>
                          <div className="text-purple-200 text-sm">TypeScript</div>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3 text-center">
                          <div className="text-2xl font-bold text-white">5+</div>
                          <div className="text-purple-200 text-sm">Components</div>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3 text-center">
                          <div className="text-2xl font-bold text-white">10+</div>
                          <div className="text-purple-200 text-sm">Features</div>
                        </div>
                        <div className="bg-purple-800/30 rounded-lg p-3 text-center">
                          <div className="text-2xl font-bold text-white">∞</div>
                          <div className="text-purple-200 text-sm">Possibilities</div>
                        </div>
                      </div>
                    </div>

                    {/* Conclusion */}
                    <div>
                      <h3 className="text-lg font-semibold text-purple-200 mb-3">🎉 Conclusion</h3>
                      <p className="text-gray-300 leading-relaxed">
                        This project demonstrates modern web development practices using React, Next.js, and TypeScript.
                        It showcases API integration, responsive design, and user experience optimization. The
                        application serves as both a useful tool for exploring GitHub profiles and a portfolio piece
                        demonstrating full-stack development capabilities.
                      </p>
                      <p className="text-gray-300 leading-relaxed mt-3">
                        The project emphasizes clean code architecture, performance optimization, and accessibility
                        standards. It represents a commitment to creating high-quality, user-centric applications that
                        solve real-world problems while maintaining excellent code quality and design principles.
                      </p>
                      <div className="mt-4 p-3 bg-gradient-to-r from-purple-800/50 to-blue-800/50 rounded-lg">
                        <p className="text-purple-200 text-center font-medium">
                          Built with ❤️ by Chanumolu Vamsi • © 2025
                        </p>
                        <p className="text-purple-300 text-center text-sm mt-1">
                          Thank you for exploring this project!
                        </p>
                      </div>
                    </div>
                  </div>
                </ScrollArea>
              </DialogContent>
            </Dialog>
          </div>

          {/* Header - Matching the screenshot exactly */}
          <div className="text-center mb-12">
            <div className="flex items-center justify-center gap-3 mb-4">
              <Github className="w-12 h-12 text-white" />
              <h1 className="text-3xl font-bold text-white">GitHub Profile Explorer</h1>
            </div>
            <p className="text-purple-200 text-lg">Discover and explore GitHub profiles with enhanced features</p>
          </div>
        </div>

        {/* Search Form - Matching the screenshot design */}
        <div className="max-w-4xl mx-auto mb-12">
          <div className="flex gap-4 items-center">
            <div className="flex-1 relative">
              <Search className="absolute left-4 top-1/2 transform -translate-y-1/2 text-purple-300 w-5 h-5" />
              <Input
                type="text"
                placeholder="Search GitHub username..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                onKeyPress={(e) => e.key === "Enter" && searchUser()}
                className="pl-12 h-12 text-base bg-purple-800/40 border-purple-600/50 text-white placeholder-purple-300 rounded-xl focus:border-purple-500 focus:ring-purple-500"
              />
            </div>
            <Button
              onClick={searchUser}
              disabled={loading}
              className="h-12 px-8 text-base bg-purple-700 hover:bg-purple-600 rounded-xl font-semibold"
            >
              {loading ? "Searching..." : "Search"}
            </Button>
          </div>
        </div>

        <div className="flex gap-8">
          {/* Sidebar - Recent Searches with Delete Options */}
          {searchHistory.length > 0 && (
            <div className="w-64">
              <Card className="bg-purple-800/30 border-purple-600/50 backdrop-blur-sm">
                <CardHeader className="flex flex-row items-center justify-between py-3">
                  <CardTitle className="text-white flex items-center gap-2 text-sm">📝 Recent Searches</CardTitle>
                  <Button
                    variant="ghost"
                    size="sm"
                    onClick={clearAllHistory}
                    className="text-purple-300 hover:text-red-400 hover:bg-red-900/30 p-1 h-auto"
                    title="Clear all searches"
                  >
                    <Trash2 className="w-4 h-4" />
                  </Button>
                </CardHeader>
                <CardContent className="py-2">
                  <div className="space-y-1">
                    {searchHistory.map((username, index) => (
                      <div
                        key={index}
                        className="flex items-center justify-between group bg-purple-700/30 hover:bg-purple-700/50 rounded-md p-2 transition-colors"
                      >
                        <button
                          onClick={() => handleHistoryClick(username)}
                          className="flex-1 text-left text-purple-200 hover:text-white text-sm truncate pr-2"
                          title={username}
                        >
                          {username}
                        </button>
                        <Button
                          variant="ghost"
                          size="sm"
                          onClick={(e) => deleteFromHistory(username, e)}
                          className="opacity-0 group-hover:opacity-100 transition-opacity text-purple-300 hover:text-red-400 hover:bg-red-900/30 p-1 h-auto flex-shrink-0"
                        >
                          <Trash2 className="w-3 h-3" />
                        </Button>
                      </div>
                    ))}
                  </div>
                </CardContent>
              </Card>
            </div>
          )}

          {/* Main Content */}
          <div className="flex-1">
            {error && (
              <Card className="bg-red-900/30 border-red-600/50 mb-6 backdrop-blur-sm">
                <CardContent className="pt-6">
                  <p className="text-red-200 text-center">{error}</p>
                </CardContent>
              </Card>
            )}

            {/* Empty State - Matching the screenshot exactly */}
            {!user && !loading && !error && (
              <div className="flex justify-center">
                <Card className="bg-purple-800/30 border-purple-600/50 backdrop-blur-sm w-full max-w-2xl">
                  <CardContent className="flex flex-col items-center justify-center py-20">
                    <div className="mb-8">
                      <Github className="w-24 h-24 text-purple-300" />
                    </div>
                    <h3 className="text-xl font-bold mb-4 text-white">Search for a GitHub User</h3>
                    <p className="text-purple-200 text-base">Enter a username above to get started</p>
                  </CardContent>
                </Card>
              </div>
            )}

            {user && (
              <div className="space-y-8">
                {/* User Profile Card */}
                <Card className="bg-purple-800/30 border-purple-600/50 backdrop-blur-sm">
                  <CardContent className="pt-8">
                    <div className="flex items-start gap-8">
                      <Avatar className="w-40 h-40 border-4 border-purple-500/50">
                        <AvatarImage src={user.avatar_url || "/placeholder.svg"} alt={user.login} />
                        <AvatarFallback className="text-6xl bg-purple-700">👤</AvatarFallback>
                      </Avatar>

                      <div className="flex-1">
                        <div className="mb-6">
                          <h2 className="text-2xl font-bold text-white mb-2">{user.name || user.login}</h2>
                          <p className="text-purple-300 text-lg">@{user.login}</p>
                          {user.bio && <p className="text-gray-300 mt-4 text-base leading-relaxed">{user.bio}</p>}
                        </div>

                        <div className="flex gap-12 mb-6">
                          <div className="text-center">
                            <div className="text-2xl font-bold text-white">{user.public_repos}</div>
                            <div className="text-purple-300">Repositories</div>
                          </div>
                          <div className="text-center">
                            <div className="text-2xl font-bold text-white">{user.followers}</div>
                            <div className="text-purple-300">Followers</div>
                          </div>
                          <div className="text-center">
                            <div className="text-2xl font-bold text-white">{user.following}</div>
                            <div className="text-purple-300">Following</div>
                          </div>
                        </div>

                        <div className="space-y-3">
                          {user.location && (
                            <div className="flex items-center gap-3 text-purple-200">
                              <MapPin className="w-5 h-5" />
                              <span className="text-base">{user.location}</span>
                            </div>
                          )}
                          {user.company && (
                            <div className="flex items-center gap-3 text-purple-200">
                              <Building className="w-5 h-5" />
                              <span className="text-base">{user.company}</span>
                            </div>
                          )}
                          {user.blog && (
                            <div className="flex items-center gap-3 text-purple-200">
                              <Link className="w-5 h-5" />
                              <a
                                href={user.blog}
                                target="_blank"
                                rel="noopener noreferrer"
                                className="hover:text-white text-base transition-colors"
                              >
                                {user.blog}
                              </a>
                            </div>
                          )}
                        </div>
                      </div>
                    </div>
                  </CardContent>
                </Card>

                {/* Repositories */}
                {repos.length > 0 && (
                  <Card className="bg-purple-800/30 border-purple-600/50 backdrop-blur-sm">
                    <CardHeader>
                      <CardTitle className="text-white flex items-center gap-3 text-xl">
                        📚 Recent Repositories
                      </CardTitle>
                    </CardHeader>
                    <CardContent>
                      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
                        {repos.map((repo) => (
                          <Card
                            key={repo.id}
                            className="bg-purple-700/30 border-purple-500/50 hover:bg-purple-700/50 transition-all duration-200 cursor-pointer hover:scale-105"
                          >
                            <CardContent className="pt-6">
                              <div className="mb-4">
                                <h4 className="font-bold text-white text-lg hover:text-purple-200 transition-colors">
                                  <a href={repo.html_url} target="_blank" rel="noopener noreferrer">
                                    {repo.name}
                                  </a>
                                </h4>
                                {repo.description && (
                                  <p className="text-purple-200 mt-2 line-clamp-2 leading-relaxed">
                                    {repo.description}
                                  </p>
                                )}
                              </div>

                              <div className="flex items-center justify-between">
                                <div className="flex items-center gap-4">
                                  {repo.language && (
                                    <Badge variant="secondary" className="bg-purple-600/50 text-purple-100 px-3 py-1">
                                      {repo.language}
                                    </Badge>
                                  )}
                                  <div className="flex items-center gap-1 text-purple-300">
                                    <Star className="w-4 h-4" />
                                    <span>{repo.stargazers_count}</span>
                                  </div>
                                  <div className="flex items-center gap-1 text-purple-300">
                                    <GitFork className="w-4 h-4" />
                                    <span>{repo.forks_count}</span>
                                  </div>
                                </div>
                              </div>
                            </CardContent>
                          </Card>
                        ))}
                      </div>
                    </CardContent>
                  </Card>
                )}
              </div>
            )}
          </div>
        </div>
        {/* Bottom Navbar/Footer */}
        <footer className="mt-16 border-t border-purple-600/30 pt-8">
          <div className="text-center">
            <p className="text-purple-300 text-sm">
              © 2025 GitHub Profile Explorer by Chanumolu Vamsi. All rights reserved.
            </p>
            <p className="text-purple-400 text-xs mt-2">Built with ❤️ using React and GitHub API</p>
            <p className="text-purple-500 text-xs mt-2">
              For any queries, contact:{" "}
              <a href="mailto:vamsichanumolu72@gmail.com" className="underline">
                vamsichanumolu72@gmail.com
              </a>
            </p>
          </div>
        </footer>
      </div>
    </div>
  )
}
